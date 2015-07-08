package org.endeavourhealth.cim.dataprotocols.admin.data;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.models.AuditItem;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.dataprotocols.admin.models.Identifier;
import org.endeavourhealth.cim.dataprotocols.admin.models.Organisation;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.serializer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrganisationRepository extends Repository {
    private static final String ORGANISATION_TABLE = "organisation";
    private static final String ORGANISATION_SEARCH_TERM_TABLE = "organisation_search_term";

    private HelperForKeyAndBlobTables<Organisation> lazyLoadedOrganisationTableHelper;
    private HelperForSearchTermTables lazyLoadedSearchTermTableHelper;

    public OrganisationRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(Organisation organisation, UUID userId) throws RepositoryException {
        if (organisation == null)
            throw new IllegalArgumentException("organisation is null");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addOrganisationInsertToBatch(batch, organisation);
            insertSearchTerms(batch, organisation);
            addAuditToBatch(batch, organisation, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Organisation organisation, UUID userId) throws RepositoryException {
        if (organisation == null)
            throw new IllegalArgumentException("organisation is null");

        Organisation organisationBeforeUpdate = getById(organisation.getId());

        if (organisationBeforeUpdate == null)
            throw new RepositoryException("Organisation not found: " + organisation.getId().toString());

        if (!organisationBeforeUpdate.getId().equals(organisation.getId()))
            throw new RepositoryException("Organisation ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addOrganisationUpdateToBatch(batch, organisation);
            updateSearchTerms(batch, organisationBeforeUpdate, organisation);
            addAuditToBatch(batch, organisation, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public Organisation getById(UUID organisationId) throws RepositoryException {
        return getOrganisationTableHelper().getById(organisationId);
    }

    public List<Organisation> getMultipleById(List<UUID> organisationIds) throws RepositoryException {
        return getOrganisationTableHelper().getMultipleById(organisationIds);
    }

    public List<Organisation> getAll() throws RepositoryException {
        return getOrganisationTableHelper().getAll();
    }

    public List<Organisation> getByIdentifier(Identifier.IdentifierType identifierType, String identifierValue) throws RepositoryException {
        if (identifierType == null)
            throw new IllegalArgumentException("identifierType is null");

        List<UUID> ids = getSearchTermTableHelper().search(identifierType.name(), identifierValue);
        return getMultipleById(ids);
    }

    public List<Organisation> getByIdentifier(String identifierValue, Identifier.IdentifierType identifierType) throws RepositoryException {
        if (identifierType == null)
            throw new IllegalArgumentException("identifierType is null");

        List<UUID> ids = getSearchTermTableHelper().search(identifierType.name(), identifierValue);
        return getMultipleById(ids);
    }

    public List<AuditItem<Organisation>> getAuditHistory(UUID organisationId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, ORGANISATION_TABLE, organisationId, Organisation.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addOrganisationInsertToBatch(BatchStatement batch, Organisation organisation) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, ORGANISATION_TABLE)
                .addColumnUUID("id", organisation.getId())
                .addColumnString("schema_version", Organisation.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(organisation))
                .build();

        batch.add(boundStatement);
    }

    private void addOrganisationUpdateToBatch(BatchStatement batch, Organisation organisation) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, ORGANISATION_TABLE, clause)
                .addColumnString("schema_version", Organisation.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(organisation))
                .addParameterUUID("id", organisation.getId())
                .build();

        batch.add(boundStatement);
    }

    private void insertSearchTerms(BatchStatement batch, Organisation organisation) {
        List<SearchPair> searchPairs = getOrganisationSearchTerms(organisation);

        getSearchTermTableHelper().insert(
                batch,
                organisation.getId(),
                searchPairs);
    }

    private void updateSearchTerms(BatchStatement batch, Organisation currentOrganisation, Organisation updatedOrganisation) {
        if (currentOrganisation.getId() != updatedOrganisation.getId())
            throw new IllegalArgumentException("IDs do not match");

        List<SearchPair> currentIdentifiers = getOrganisationSearchTerms(currentOrganisation);
        List<SearchPair> updatedIdentifiers = getOrganisationSearchTerms(updatedOrganisation);

        getSearchTermTableHelper().updateSearchTerms(
                batch,
                currentOrganisation.getId(),
                currentIdentifiers,
                updatedIdentifiers);
    }

    private List<SearchPair> getOrganisationSearchTerms(Organisation organisation) {

        List<SearchPair> pairs = new ArrayList<>();

        if (organisation.getIdentifiers() != null) {
            for (Identifier id : organisation.getIdentifiers()) {
                SearchPair pair = new SearchPair(id.getType().name(), id.getValue());
                pairs.add(pair);
            }
        }

        List<SearchPair> splitName = getSearchTermTableHelper().splitText("name", organisation.getName());
        pairs.addAll(splitName);

        return pairs;
    }

    private HelperForKeyAndBlobTables<Organisation> getOrganisationTableHelper() {
        if (lazyLoadedOrganisationTableHelper == null) {
            lazyLoadedOrganisationTableHelper = new HelperForKeyAndBlobTables<Organisation>(
                    session,
                    statementCache,
                    ORGANISATION_TABLE,
                    this::convertToOrganisation);
        }

        return lazyLoadedOrganisationTableHelper;
    }

    private HelperForSearchTermTables getSearchTermTableHelper() {
        if (lazyLoadedSearchTermTableHelper == null) {
            lazyLoadedSearchTermTableHelper = new HelperForSearchTermTables(
                    session,
                    statementCache,
                    ORGANISATION_SEARCH_TERM_TABLE,
                    "organisation_id");
        }

        return lazyLoadedSearchTermTableHelper;
    }

    private Organisation convertToOrganisation(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(Organisation.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Organisation> searchByName(List<String> searchTerms) throws RepositoryException {
        List<UUID> ids = getSearchTermTableHelper().search("name", searchTerms);
        return getMultipleById(ids);
    }

    private void addAuditToBatch(BatchStatement batch, Organisation organisation, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(
                statementCache,
                ORGANISATION_TABLE,
                organisation.getId(),
                userId,
                mode,
                Organisation.SCHEMA_VERSION,
                JsonSerializer.serialize(organisation));

        batch.add(boundStatement);
    }
}
