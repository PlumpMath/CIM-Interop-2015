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
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;
import org.endeavourhealth.cim.dataprotocols.protocol.models.CodeCluster;

import java.util.List;
import java.util.UUID;

public class CodeClusterRepository extends Repository {

    private static final String CODE_CLUSTER_TABLE = "code_cluster";

    private HelperForKeyAndBlobTables<CodeCluster> lazyLoadedCodeClusterTableHelper;

    public CodeClusterRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(CodeCluster codeCluster, UUID userId) throws RepositoryException {
        if (codeCluster == null)
            throw new IllegalArgumentException("codeCluster is null");

        try {
            BatchStatement batch = new BatchStatement();

            addCodeClusterInsertToBatch(batch, codeCluster);
            addAuditToBatch(batch, codeCluster, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(CodeCluster codeCluster, UUID userId) throws RepositoryException {
        if (codeCluster == null)
            throw new IllegalArgumentException("codeCluster is null");

        CodeCluster codeClusterBeforeUpdate = getById(codeCluster.getId());

        if (codeClusterBeforeUpdate == null)
            throw new RepositoryException("Code Cluster not found: " + codeCluster.getId().toString());

        if (!codeClusterBeforeUpdate.getId().equals(codeCluster.getId()))
            throw new RepositoryException("Code Cluster ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addCodeClusterUpdateToBatch(batch, codeCluster);
            addAuditToBatch(batch, codeCluster, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void delete(String[] codeClusters, UUID userId) throws RepositoryException{
        if (codeClusters == null)
            throw new IllegalArgumentException("codeClusters is null");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            for(String codeClusterId : codeClusters) {
                UUID codeClusterUUID = UUID.fromString(codeClusterId);
                CodeCluster codeClusterToDelete = getById(codeClusterUUID);

                if (codeClusterToDelete == null)
                    throw new RepositoryException("Code Cluster not found: " + codeClusterId);

                if (!codeClusterToDelete.getId().equals(codeClusterUUID))
                    throw new RepositoryException("Code Cluster ids do not match");

                addCodeClusterDeleteToBatch(batch, codeClusterToDelete);
                addAuditToBatch(batch, codeClusterToDelete, userId, AuditMode.delete);
            }

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }


    public List<CodeCluster> getAll() throws RepositoryException {
        return getCodeClusterTableHelper().getAll();
    }

    private void addCodeClusterInsertToBatch(BatchStatement batch, CodeCluster codeCluster) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, CODE_CLUSTER_TABLE)
                .addColumnUUID("id", codeCluster.getId())
                .addColumnString("schema_version", CodeCluster.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(codeCluster))
                .build();

        batch.add(boundStatement);
    }

    private void addCodeClusterUpdateToBatch(BatchStatement batch, CodeCluster codeCluster) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, CODE_CLUSTER_TABLE, clause)
                .addColumnString("schema_version", CodeCluster.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(codeCluster))
                .addParameterUUID("id", codeCluster.getId())
                .build();

        batch.add(boundStatement);
    }

    private void addCodeClusterDeleteToBatch(BatchStatement batch, CodeCluster codeCluster) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new DeleteStatementBuilder(statementCache, CODE_CLUSTER_TABLE, clause)
                .addParameterUUID("id", codeCluster.getId())
                .build();

        batch.add(boundStatement);
    }

    public CodeCluster getById(UUID id) throws RepositoryException {
        return getCodeClusterTableHelper().getById(id);
    }

    public List<CodeCluster> getMultipleById(List<UUID> ids) throws RepositoryException {
        return getCodeClusterTableHelper().getMultipleById(ids);
    }

    public List<AuditItem<CodeCluster>> getAuditHistory(UUID codeClusterId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, CODE_CLUSTER_TABLE, codeClusterId, CodeCluster.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<CodeCluster> getCodeClusterTableHelper() {
        if (lazyLoadedCodeClusterTableHelper == null) {
            lazyLoadedCodeClusterTableHelper = new HelperForKeyAndBlobTables<>(
                    session,
                    statementCache,
                    CODE_CLUSTER_TABLE,
                    this::convertToCodeCluster);
        }

        return lazyLoadedCodeClusterTableHelper;
    }

    private CodeCluster convertToCodeCluster(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(CodeCluster.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, CodeCluster codeCluster, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                CODE_CLUSTER_TABLE,
                codeCluster.getId(),
                userId,
                mode,
                CodeCluster.SCHEMA_VERSION,
                JsonSerializer.serialize(codeCluster));

        batch.add(boundStatement);
    }
}
