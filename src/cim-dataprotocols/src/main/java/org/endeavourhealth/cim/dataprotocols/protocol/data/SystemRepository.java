package org.endeavourhealth.cim.dataprotocols.protocol.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.models.AuditItem;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.common.serializer.*;
import org.endeavourhealth.cim.dataprotocols.protocol.models.System;

import java.util.List;
import java.util.UUID;

public class SystemRepository extends Repository {

    private static final String SYSTEM_TABLE = "system";

    private HelperForKeyAndBlobTables<System> lazyLoadedSystemTableHelper;

    public SystemRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(System system, UUID userId) throws RepositoryException {
        if (system == null)
            throw new IllegalArgumentException("system is null");

        try {
            BatchStatement batch = new BatchStatement();

            addSystemInsertToBatch(batch, system);
            addAuditToBatch(batch, system, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(System system, UUID userId) throws RepositoryException {
        if (system == null)
            throw new IllegalArgumentException("system is null");

        System systemBeforeUpdate = getById(system.getId());

        if (systemBeforeUpdate == null)
            throw new RepositoryException("System not found: " + system.getId().toString());

        if (!systemBeforeUpdate.getId().equals(system.getId()))
            throw new RepositoryException("System ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addSystemUpdateToBatch(batch, system);
            addAuditToBatch(batch, system, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addSystemInsertToBatch(BatchStatement batch, System system) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, SYSTEM_TABLE)
                .addColumnUUID("id", system.getId())
                .addColumnString("schema_version", System.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(system))
                .build();

        batch.add(boundStatement);
    }

    private void addSystemUpdateToBatch(BatchStatement batch, System system) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, SYSTEM_TABLE, clause)
                .addColumnString("schema_version", System.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(system))
                .addParameterUUID("id", system.getId())
                .build();

        batch.add(boundStatement);
    }

    public System getById(UUID id) throws RepositoryException {
        return getSystemTableHelper().getById(id);
    }

    public List<System> getMultipleById(List<UUID> ids) throws RepositoryException {
        return getSystemTableHelper().getMultipleById(ids);
    }

    public List<System> getAll() throws RepositoryException {
        return getSystemTableHelper().getAll();
    }

    public List<AuditItem<System>> getAuditHistory(UUID systemId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, SYSTEM_TABLE, systemId, System.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<System> getSystemTableHelper() {
        if (lazyLoadedSystemTableHelper == null) {
            lazyLoadedSystemTableHelper = new HelperForKeyAndBlobTables<System>(
                    session,
                    statementCache,
                    SYSTEM_TABLE,
                    this::convertToSystem);
        }

        return lazyLoadedSystemTableHelper;
    }

    private System convertToSystem(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(System.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, System system, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                SYSTEM_TABLE,
                system.getId(),
                userId,
                mode,
                System.SCHEMA_VERSION,
                JsonSerializer.serialize(system));

        batch.add(boundStatement);
    }
}
