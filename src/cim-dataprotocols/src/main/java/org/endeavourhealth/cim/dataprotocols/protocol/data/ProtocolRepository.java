package org.endeavourhealth.cim.dataprotocols.protocol.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.querybuilder.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.models.AuditItem;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;
import org.endeavourhealth.cim.dataprotocols.protocol.models.CodeCluster;
import org.endeavourhealth.cim.dataprotocols.protocol.models.Protocol;

import java.util.List;
import java.util.UUID;

public class ProtocolRepository extends Repository {

    private static final String PROTOCOL_TABLE = "protocol";

    private HelperForKeyAndBlobTables<Protocol> lazyLoadedProtocolTableHelper;

    public ProtocolRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(Protocol protocol, UUID userId) throws RepositoryException {
        if (protocol == null)
            throw new IllegalArgumentException("protocol is null");

        try {
            BatchStatement batch = new BatchStatement();

            addProtocolInsertToBatch(batch, protocol);
            addAuditToBatch(batch, protocol, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Protocol protocol, UUID userId) throws RepositoryException {
        if (protocol == null)
            throw new IllegalArgumentException("protocol is null");

        Protocol protocolBeforeUpdate = getById(protocol.getId());

        if (protocolBeforeUpdate == null)
            throw new RepositoryException("Protocol not found: " + protocol.getId().toString());

        if (!protocolBeforeUpdate.getId().equals(protocol.getId()))
            throw new RepositoryException("Protocol ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addProtocolUpdateToBatch(batch, protocol);
            addAuditToBatch(batch, protocol, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addProtocolInsertToBatch(BatchStatement batch, Protocol protocol) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, PROTOCOL_TABLE)
                .addColumnUUID("id", protocol.getId())
                .addColumnString("schema_version", Protocol.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(protocol))
                .build();

        batch.add(boundStatement);
    }

    private void addProtocolUpdateToBatch(BatchStatement batch, Protocol protocol) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, PROTOCOL_TABLE, clause)
                .addColumnString("schema_version", Protocol.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(protocol))
                .addParameterUUID("id", protocol.getId())
                .build();

        batch.add(boundStatement);
    }

    public List<Protocol> getAll() throws RepositoryException {
        return getProtocolTableHelper().getAll();
    }

    public Protocol getById(UUID id) throws RepositoryException {
        return getProtocolTableHelper().getById(id);
    }

    public List<Protocol> getMultipleById(List<UUID> ids) throws RepositoryException {
        return getProtocolTableHelper().getMultipleById(ids);
    }

    public List<AuditItem<Protocol>> getAuditHistory(UUID protocolId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, PROTOCOL_TABLE, protocolId, Protocol.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<Protocol> getProtocolTableHelper() {
        if (lazyLoadedProtocolTableHelper == null) {
            lazyLoadedProtocolTableHelper = new HelperForKeyAndBlobTables<Protocol>(
                    session,
                    statementCache,
                    PROTOCOL_TABLE,
                    this::convertToProtocol);
        }

        return lazyLoadedProtocolTableHelper;
    }

    private Protocol convertToProtocol(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(Protocol.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, Protocol protocol, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                PROTOCOL_TABLE,
                protocol.getId(),
                userId,
                mode,
                Protocol.SCHEMA_VERSION,
                JsonSerializer.serialize(protocol));

        batch.add(boundStatement);
    }
}
