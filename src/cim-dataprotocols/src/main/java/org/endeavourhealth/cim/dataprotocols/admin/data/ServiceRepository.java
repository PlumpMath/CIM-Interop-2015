package org.endeavourhealth.cim.dataprotocols.admin.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.models.AuditItem;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;
import org.endeavourhealth.cim.dataprotocols.admin.models.Identifier;
import org.endeavourhealth.cim.dataprotocols.admin.models.Service;
import org.endeavourhealth.cim.dataprotocols.protocol.models.Protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceRepository extends Repository {

    private static final String SERVICE_TABLE = "service";
    private static final String SERVICE_SEARCH_TERM_TABLE = "service_search_term";

    private HelperForKeyAndBlobTables<Service> lazyLoadedServiceTableHelper;
    private HelperForSearchTermTables lazyLoadedSearchTermTableHelper;

    public ServiceRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(Service service, UUID userId) throws RepositoryException {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        try {
            BatchStatement batch = new BatchStatement();

            addServiceInsertToBatch(batch, service);
            insertSearchTerms(batch, service);
            addAuditToBatch(batch, service, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Service service, UUID userId) throws RepositoryException {
        if (service == null)
            throw new IllegalArgumentException("service is null");

        Service serviceBeforeUpdate = getById(service.getId());

        if (serviceBeforeUpdate == null)
            throw new RepositoryException("Service not found: " + service.getId().toString());

        if (!serviceBeforeUpdate.getId().equals(service.getId()))
            throw new RepositoryException("Service ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addServiceUpdateToBatch(batch, service);
            updateSearchTerms(batch, serviceBeforeUpdate, service);
            addAuditToBatch(batch, service, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addServiceInsertToBatch(BatchStatement batch, Service service) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, SERVICE_TABLE)
                .addColumnUUID("id", service.getId())
                .addColumnString("schema_version", Service.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(service))
                .build();

        batch.add(boundStatement);
    }

    private void addServiceUpdateToBatch(BatchStatement batch, Service service) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, SERVICE_TABLE, clause)
                .addColumnString("schema_version", Service.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(service))
                .addParameterUUID("id", service.getId())
                .build();

        batch.add(boundStatement);
    }

    private void insertSearchTerms(BatchStatement batch, Service service) {
        List<SearchPair> searchPairs = getServiceSearchTerms(service);

        getSearchTermTableHelper().insert(
                batch,
                service.getId(),
                searchPairs);
    }

    private void updateSearchTerms(BatchStatement batch, Service current, Service updated) {
        if (current.getId() != updated.getId())
            throw new IllegalArgumentException("IDs do not match");

        List<SearchPair> currentIdentifiers = getServiceSearchTerms(current);
        List<SearchPair> updatedIdentifiers = getServiceSearchTerms(updated);

        getSearchTermTableHelper().updateSearchTerms(
                batch,
                current.getId(),
                currentIdentifiers,
                updatedIdentifiers);
    }

    private List<SearchPair> getServiceSearchTerms(Service service) {

        List<SearchPair> pairs = new ArrayList<>();

        if (service.getIdentifiers() != null) {
            for (Identifier id : service.getIdentifiers()) {
                SearchPair pair = new SearchPair(id.getType().name(), id.getValue());
                pairs.add(pair);
            }
        }

        List<SearchPair> splitName = getSearchTermTableHelper().splitText("name", service.getName());
        pairs.addAll(splitName);

        return pairs;
    }

    public List<Service> getAll() throws RepositoryException {
        return getServiceTableHelper().getAll();
    }

    public Service getById(UUID serviceId) throws RepositoryException {
        return getServiceTableHelper().getById(serviceId);
    }

    public List<Service> getMultipleById(List<UUID> serviceIds) throws RepositoryException {
        return getServiceTableHelper().getMultipleById(serviceIds);
    }

    public List<AuditItem<Service>> getAuditHistory(UUID serviceId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, SERVICE_TABLE, serviceId, Service.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<Service> getServiceTableHelper() {
        if (lazyLoadedServiceTableHelper == null) {
            lazyLoadedServiceTableHelper = new HelperForKeyAndBlobTables<Service>(
                    session,
                    statementCache,
                    SERVICE_TABLE,
                    this::convertToService);
        }

        return lazyLoadedServiceTableHelper;
    }

    private HelperForSearchTermTables getSearchTermTableHelper() {
        if (lazyLoadedSearchTermTableHelper == null) {
            lazyLoadedSearchTermTableHelper = new HelperForSearchTermTables(
                    session,
                    statementCache,
                    SERVICE_SEARCH_TERM_TABLE,
                    "service_id");
        }

        return lazyLoadedSearchTermTableHelper;
    }

    private Service convertToService(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(Service.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    public List<Service> searchByName(List<String> searchTerms) throws RepositoryException {
        List<UUID> ids = getSearchTermTableHelper().search("name", searchTerms);
        return getMultipleById(ids);
    }

    private void addAuditToBatch(BatchStatement batch, Service service, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                SERVICE_TABLE,
                service.getId(),
                userId,
                mode,
                Service.SCHEMA_VERSION,
                JsonSerializer.serialize(service));

        batch.add(boundStatement);
    }
}
