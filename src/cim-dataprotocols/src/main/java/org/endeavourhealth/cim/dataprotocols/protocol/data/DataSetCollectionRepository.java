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
import org.endeavourhealth.cim.dataprotocols.protocol.models.DataSetCollection;

import java.util.List;
import java.util.UUID;

public class DataSetCollectionRepository extends Repository {
    private static final String DATASET_COLLECTION_TABLE = "dataset_collection";

    private HelperForKeyAndBlobTables<DataSetCollection> lazyLoadedDataSetCollectionTableHelper;

    public DataSetCollectionRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(DataSetCollection dataSetCollection, UUID userId) throws RepositoryException {
        if (dataSetCollection == null)
            throw new IllegalArgumentException("dataSetCollection is null");

        try {
            BatchStatement batch = new BatchStatement();

            addDataSetCollectionInsertToBatch(batch, dataSetCollection);
            addAuditToBatch(batch, dataSetCollection, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(DataSetCollection dataSetCollection, UUID userId) throws RepositoryException {
        if (dataSetCollection == null)
            throw new IllegalArgumentException("dataSetCollection is null");

        DataSetCollection dataSetCollectionBeforeUpdate = getById(dataSetCollection.getId());

        if (dataSetCollectionBeforeUpdate == null)
            throw new RepositoryException("DataSetCollection not found: " + dataSetCollection.getId().toString());

        if (!dataSetCollectionBeforeUpdate.getId().equals(dataSetCollection.getId()))
            throw new RepositoryException("DataSetCollection ids do not match");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            addDataSetCollectionUpdateToBatch(batch, dataSetCollection);
            addAuditToBatch(batch, dataSetCollection, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void delete(String[] dataSetCollections, UUID userId) throws RepositoryException{
        if (dataSetCollections == null)
            throw new IllegalArgumentException("dataSetCollections is null");

        try {
            // create logged batch
            BatchStatement batch = new BatchStatement();

            for(String dataSetCollectionId : dataSetCollections) {
                UUID dataSetCollectionUUID = UUID.fromString(dataSetCollectionId);
                DataSetCollection dataSetCollectionToDelete = getById(dataSetCollectionUUID);

                if (dataSetCollectionToDelete == null)
                    throw new RepositoryException("Data set Collection not found: " + dataSetCollectionId);

                if (!dataSetCollectionToDelete.getId().equals(dataSetCollectionUUID))
                    throw new RepositoryException("Data Set Collection ids do not match");

                addDataSetCollectionDeleteToBatch(batch, dataSetCollectionToDelete);
                addAuditToBatch(batch, dataSetCollectionToDelete, userId, AuditMode.delete);
            }

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addDataSetCollectionInsertToBatch(BatchStatement batch, DataSetCollection dataSetCollection) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, DATASET_COLLECTION_TABLE)
                .addColumnUUID("id", dataSetCollection.getId())
                .addColumnString("schema_version", dataSetCollection.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(dataSetCollection))
                .build();

        batch.add(boundStatement);
    }

    private void addDataSetCollectionUpdateToBatch(BatchStatement batch, DataSetCollection dataSetCollection) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, DATASET_COLLECTION_TABLE, clause)
                .addColumnString("schema_version", dataSetCollection.SCHEMA_VERSION)
                .addColumnString("data", JsonSerializer.serialize(dataSetCollection))
                .addParameterUUID("id", dataSetCollection.getId())
                .build();

        batch.add(boundStatement);
    }

    private void addDataSetCollectionDeleteToBatch(BatchStatement batch, DataSetCollection dataSetCollection) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("id", QueryBuilder.bindMarker("id"));

        BoundStatement boundStatement = new DeleteStatementBuilder(statementCache, DATASET_COLLECTION_TABLE, clause)
                .addParameterUUID("id", dataSetCollection.getId())
                .build();

        batch.add(boundStatement);
    }

    public DataSetCollection getById(UUID id) throws RepositoryException {
        return getDataSetCollectionTableHelper().getById(id);
    }

    public List<DataSetCollection> getMultipleById(List<UUID> ids) throws RepositoryException {
        return getDataSetCollectionTableHelper().getMultipleById(ids);
    }

    public List<DataSetCollection> getAll() throws RepositoryException {
        return getDataSetCollectionTableHelper().getAll();
    }

    public List<AuditItem<DataSetCollection>> getAuditHistory(UUID dataSetCollectionId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, DATASET_COLLECTION_TABLE, dataSetCollectionId, DataSetCollection.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private HelperForKeyAndBlobTables<DataSetCollection> getDataSetCollectionTableHelper() {
        if (lazyLoadedDataSetCollectionTableHelper == null) {
            lazyLoadedDataSetCollectionTableHelper = new HelperForKeyAndBlobTables<DataSetCollection>(
                    session,
                    statementCache,
                    DATASET_COLLECTION_TABLE,
                    this::convertToDataSetCollection);
        }

        return lazyLoadedDataSetCollectionTableHelper;
    }

    private DataSetCollection convertToDataSetCollection(String version, String data) throws RepositoryException {
        try {
            return JsonSerializer.deserialize(DataSetCollection.class, data);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, DataSetCollection dataSetCollection, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(statementCache,
                DATASET_COLLECTION_TABLE,
                dataSetCollection.getId(),
                userId,
                mode,
                DataSetCollection.SCHEMA_VERSION,
                JsonSerializer.serialize(dataSetCollection));

        batch.add(boundStatement);
    }
}
