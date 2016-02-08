package org.endeavourhealth.core.repository.nodes.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.core.repository.common.data.*;
import org.endeavourhealth.core.repository.nodes.models.InboundQueueNode;
import org.endeavourhealth.core.repository.nodes.models.Node;
import org.endeavourhealth.core.repository.nodes.models.NodePurpose;
import org.endeavourhealth.core.utils.StreamExtension;
import org.endeavourhealth.core.repository.DataConfiguration;
import org.endeavourhealth.core.repository.common.model.AuditItem;
import org.endeavourhealth.core.repository.common.model.AuditMode;
import org.endeavourhealth.core.serializer.DeserializationException;
import org.endeavourhealth.core.serializer.JsonSerializer;

import java.util.List;
import java.util.UUID;

public class NodeRepository extends Repository {

    private static final String NODE_TABLE = "node";

    public NodeRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void add(Node node, UUID userId) throws RepositoryException {
        if (node == null)
            throw new IllegalArgumentException("node is null");

        try {
            BatchStatement batch = new BatchStatement();

            addNodeInsertToBatch(batch, node);
            addAuditToBatch(batch, node, userId, AuditMode.add);

            getSession().execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Node node, UUID userId) throws RepositoryException {
        if (node == null)
            throw new IllegalArgumentException("node is null");

        Node nodeBeforeUpdate = getById(node.getPurpose(), node.getId());

        if (nodeBeforeUpdate == null)
            throw new RepositoryException("Node not found: " + node.getId().toString());

        if (!nodeBeforeUpdate.getId().equals(node.getId()))
            throw new RepositoryException("Node ids do not match");

        if (!nodeBeforeUpdate.getPurpose().equals(node.getPurpose()))
            throw new RepositoryException("Node purposes do not match");

        try {
            BatchStatement batch = new BatchStatement();

            addNodeUpdateToBatch(batch, node);
            addAuditToBatch(batch, node, userId, AuditMode.edit);

            getSession().execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addNodeInsertToBatch(BatchStatement batch, Node node) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(getStatementCache(), NODE_TABLE)
                .addColumnString("purpose", node.getPurpose().name())
                .addColumnUUID("id", node.getId())
                .addColumnString("schema_version", node.getSchemaVersion())
                .addColumnString("data", JsonSerializer.serialize(node))
                .build();

        batch.add(boundStatement);
    }

    private void addNodeUpdateToBatch(BatchStatement batch, Node node) throws JsonProcessingException {

        Clause[] clauses = new Clause[]{
                QueryBuilder.eq("purpose", QueryBuilder.bindMarker("purpose")),
                QueryBuilder.eq("id", QueryBuilder.bindMarker("id"))
        };

        BoundStatement boundStatement = new UpdateStatementBuilder(getStatementCache(), NODE_TABLE, clauses)
                .addColumnString("schema_version", node.getSchemaVersion())
                .addColumnString("data", JsonSerializer.serialize(node))
                .addParameterUUID("id", node.getId())
                .addParameterString("purpose", node.getPurpose().name())
                .build();

        batch.add(boundStatement);
    }

    public Node getById(NodePurpose purpose, UUID nodeId) throws RepositoryException {

        PreparedStatement preparedStatement = getStatementCache().getOrAdd(QueryBuilder.select()
                .all()
                .from(NODE_TABLE)
                .where(QueryBuilder.eq("id", QueryBuilder.bindMarker("id")))
                .and(QueryBuilder.eq("purpose", QueryBuilder.bindMarker("purpose"))));

        BoundStatement boundStatement = preparedStatement
                .bind()
                .setUUID("id", nodeId)
                .setString("purpose", purpose.name());

        Row row = getSession().execute(boundStatement)
                .all()
                .stream()
                .collect(StreamExtension.singleOrNullCollector());

        if (row == null)
            return null;

        return convertToNode(row.getString("purpose"), row.getString("schema_version"), row.getString("data"));
    }

    private Node convertToNode(String purpose, String version, String data) throws RepositoryException {

        NodePurpose purposeValue = NodePurpose.valueOf(purpose);

        try {
            switch (purposeValue) {
                case InboundQueue:
                    return JsonSerializer.deserialize(InboundQueueNode.class, data);
                default:
                    throw new IllegalArgumentException("Node purpose not supported: " + purpose);
            }
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    public List<AuditItem<Node>> getAuditHistory(UUID nodeId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(getSession(), getStatementCache(), NODE_TABLE, nodeId, Node.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(BatchStatement batch, Node node, UUID userId, AuditMode mode) throws JsonProcessingException {
        BoundStatement boundStatement = AuditHelper.buildAuditStatement(
                getStatementCache(),
                NODE_TABLE,
                node.getId(),
                userId,
                mode,
                node.getSchemaVersion(),
                JsonSerializer.serialize(node));

        batch.add(boundStatement);
    }
}
