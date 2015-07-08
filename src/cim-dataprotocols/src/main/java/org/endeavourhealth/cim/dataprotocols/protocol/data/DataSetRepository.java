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
import org.endeavourhealth.cim.dataprotocols.protocol.models.DataSet;

import java.util.List;
import java.util.UUID;

public class DataSetRepository extends Repository {
    private static final String DATASET_TABLE = "dataset";

    private HelperForKeyAndBlobTables<DataSet> lazyLoadedDataSetTableHelper;

    public DataSetRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(DataSet dataSet, UUID userId) throws RepositoryException {
        if (dataSet == null)
            throw new IllegalArgumentException("dataSet is null");

        try {
            BatchStatement batch = new BatchStatement();

            addDataSetInsertToBatch(batch, dataSet);
            addAuditToBatch(batch, dataSet, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(DataSet dataSet, UUID userId) throws RepositoryException {
        if (dataSet == null)
            throw new IllegalArgumentException("dataSet is null");

        DataSet dataSetBeforeUpdate = getById(dataSet.getId());

        if (dataSetBeforeUpdate == null)
            throw new RepositoryException("DataSet not found: " + dataSet.getId().toString());

        if (!dataSetBeforeUpdate.getId().equals(dataSet.getId()))
            throw new RepositoryException("DataSet ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addDataSetUpdateToBatch(batch, dataSet);
            addAuditToBatch(batch, dataSet, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void delete(String[] dataSets, UUID userId) throws RepositoryException{
        if (dataSets == null)
            throw new IllegalArgumentException("dataSets is null");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            for(String dataSetId : dataSets) {
                UUID dataSetUUID = UUID.fromString(dataSetId);
                DataSet dataSetToDelete = getById(dataSetUUID);

                if (dataSetToDelete == null)
                    throw new RepositoryException("Data set not found: " + dataSetId);

                if (!dataSetToDelete.getId().equals(dataSetUUID))
                    throw new RepositoryException("Data Set ids do not match");

                addDataSetDeleteToBatch(batch, dataSetToDelete);
                addAuditToBatch(batch, dataSetToDelete, userId, AuditMode.delete);
            }

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public List<DataSet> getAll() throws RepositoryException {
        return getDataSetTableHelper().getAll();
    }

    private void addDataSetInsertToBatch(BatchStatement batch, DataSet dataSet) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, DATASET_TABLE)
                .addColumnUUID("id", dataSet.getId())
                .addColumnString("schema_version", DataSet.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(dataSet))
                .build();

        batch.add(boundStatement);
    }

    private void addDataSetUpdateToBatch(BatchStatement batch, DataSet dataSet) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, DATASET_TABLE, clause)
                .addColumnString("schema_version", DataSet.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(dataSet))
                .addParameterUUID("id", dataSet.getId())
                .build();

        batch.add(boundStatement);
    }

    private void addDataSetDeleteToBatch(BatchStatement batch, DataSet dataSet) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new DeleteStatementBuilder(statementCache, DATASET_TABLE, clause)
                .addParameterUUID("id", dataSet.getId())
                .build();

        batch.add(boundStatement);
    }

    public DataSet getById(UUID id) throws RepositoryException {
        return getDataSetTableHelper().getById(id);
    }

    public List<DataSet> getMultipleById(List<UUID> ids) throws RepositoryException {
        return getDataSetTableHelper().getMultipleById(ids);
    }

    public List<AuditItem<DataSet>> getAuditHistory(UUID dataSetId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, DATASET_TABLE, dataSetId, DataSet.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<DataSet> getDataSetTableHelper() {
        if (lazyLoadedDataSetTableHelper == null) {
            lazyLoadedDataSetTableHelper = new HelperForKeyAndBlobTables<DataSet>(
                    session,
                    statementCache,
                    DATASET_TABLE,
                    this::convertToDataSet);
        }

        return lazyLoadedDataSetTableHelper;
    }

    private DataSet convertToDataSet(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(DataSet.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, DataSet dataSet, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                DATASET_TABLE,
                dataSet.getId(),
                userId,
                mode,
                DataSet.SCHEMA_VERSION,
                JsonSerializer.serialize(dataSet));

        batch.add(boundStatement);
    }
}
