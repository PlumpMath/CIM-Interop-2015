package org.endeavourhealth.cim.common.configuration.nodes.data;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.endeavourhealth.cim.common.configuration.DataConfiguration;
import org.endeavourhealth.cim.common.configuration.nodes.models.ServiceNodeLinks;
import org.endeavourhealth.cim.common.data.*;
import org.endeavourhealth.cim.common.models.AuditItem;
import org.endeavourhealth.cim.common.models.AuditMode;
import org.endeavourhealth.cim.common.serializer.DeserializationException;
import org.endeavourhealth.cim.common.serializer.JsonSerializer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServiceNodeLinkRepository extends Repository{

    private static final String SERVICE_TO_NODE_TABLE = "service_to_node";
    private static final String NODE_TO_SERVICE_TABLE = "node_to_service";

    public ServiceNodeLinkRepository() {
        super(DataConfiguration.DATASERVICE_KEYSPACE);
    }

    public void setServiceNodesLinks(UUID serviceId, ServiceNodeLinks serviceNodeLinks, UUID userId) throws RepositoryException {
        if (serviceNodeLinks == null)
            throw new IllegalArgumentException("serviceNodeLinks is null");

        ServiceNodeLinks currentLinks = getNodesByServiceId(serviceId);

        if (currentLinks == null)
            addServiceNodeLinks(serviceId, serviceNodeLinks, userId);
        else
            updateServiceNodeLinks(serviceId, serviceNodeLinks, currentLinks, userId);
    }

    private void addServiceNodeLinks(UUID serviceId, ServiceNodeLinks serviceNodeLinks, UUID userId) throws RepositoryException {

        try {
            BatchStatement batch = new BatchStatement();

            addServiceNodeLinkInsertToBatch(batch, serviceId, serviceNodeLinks);
            updateNodeToServiceLookups(batch, serviceId, serviceNodeLinks, null);
            addAuditToBatch(batch, serviceId, serviceNodeLinks, userId, AuditMode.add);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void addNodeToServiceLookup(BatchStatement batch, UUID nodeId, UUID serviceId) {
        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, NODE_TO_SERVICE_TABLE)
                .addColumnUUID("node_id", nodeId)
                .addColumnUUID("service_id", serviceId)
                .build();

        batch.add(boundStatement);
    }

    private void deleteNodeToServiceLookup(BatchStatement batch, UUID nodeId, UUID serviceId) {

        Clause[] clauses = new Clause[] {
                QueryBuilder.eq("node_id", QueryBuilder.bindMarker("node_id")),
                QueryBuilder.eq("service_id", QueryBuilder.bindMarker("service_id"))
        };

        BoundStatement boundStatement = new DeleteStatementBuilder(statementCache, NODE_TO_SERVICE_TABLE, clauses)
            .addParameterUUID("node_id", nodeId)
            .addParameterUUID("service_id", serviceId)
            .build();

        batch.add(boundStatement);
    }

    private void addServiceNodeLinkInsertToBatch(
            BatchStatement batch,
            UUID serviceId,
            ServiceNodeLinks serviceNodeLinks) throws JsonProcessingException {

        BoundStatement boundStatement = new InsertStatementBuilder(statementCache, SERVICE_TO_NODE_TABLE)
                .addColumnUUID("service_id", serviceId)
                .addColumnString("schema_version", ServiceNodeLinks.SCHEMA_VERSION)
                .addColumnString("node_links", JsonSerializer.serialize(serviceNodeLinks))
                .build();

        batch.add(boundStatement);
    }


    private void updateServiceNodeLinks(UUID serviceId, ServiceNodeLinks serviceNodeLinks, ServiceNodeLinks currentLinks, UUID userId) throws RepositoryException {
        try {
            BatchStatement batch = new BatchStatement();

            addServiceNodeLinkUpdateToBatch(batch, serviceId, serviceNodeLinks);
            updateNodeToServiceLookups(batch, serviceId, serviceNodeLinks, currentLinks);
            addAuditToBatch(batch, serviceId, serviceNodeLinks, userId, AuditMode.edit);

            session.execute(batch);
        } catch (JsonProcessingException e) {
            throw new RepositoryException(e);
        }
    }

    private void updateNodeToServiceLookups(BatchStatement batch, UUID serviceId, ServiceNodeLinks newLinks, ServiceNodeLinks currentLinks) {

        boolean currentIsNullOrEmpty = currentLinks == null || currentLinks.getLinks() == null || currentLinks.getLinks().size() == 0;
        boolean newIsNullOrEmpty = newLinks == null || newLinks.getLinks() == null || newLinks.getLinks().size() == 0;

        if (currentIsNullOrEmpty && newIsNullOrEmpty) {
            return;
        } else if (currentIsNullOrEmpty) {

            for (ServiceNodeLinks.ServiceNodeLink link : newLinks.getLinks()) {
                addNodeToServiceLookup(batch, link.getNodeId(), serviceId);
            }

            return;

        } else if (newIsNullOrEmpty) {

            for (ServiceNodeLinks.ServiceNodeLink link : currentLinks.getLinks()) {
                deleteNodeToServiceLookup(batch, link.getNodeId(), serviceId);
            }

            return;
        }

        // delete items that have been removed by the update
        for (ServiceNodeLinks.ServiceNodeLink item : currentLinks.getLinks()) {
            if (nodeLinkNotPresent(newLinks, item)) {
                deleteNodeToServiceLookup(batch, item.getNodeId(), serviceId);
            }
        }

        // insert items that have been added by the update
        for (ServiceNodeLinks.ServiceNodeLink item : newLinks.getLinks()) {
            if (nodeLinkNotPresent(currentLinks, item)) {
                addNodeToServiceLookup(batch, item.getNodeId(), serviceId);
            }
        }
    }

    private boolean nodeLinkNotPresent(ServiceNodeLinks links, ServiceNodeLinks.ServiceNodeLink itemToCheck) {
        return links.getLinks()
                .stream()
                .noneMatch(i -> i.getNodeId().equals(itemToCheck.getNodeId()));
    }


    private void addServiceNodeLinkUpdateToBatch(BatchStatement batch, UUID serviceId, ServiceNodeLinks serviceNodeLinks) throws JsonProcessingException {

        Clause clause = QueryBuilder.eq("service_id", QueryBuilder.bindMarker("service_id"));

        BoundStatement boundStatement = new UpdateStatementBuilder(statementCache, SERVICE_TO_NODE_TABLE, clause)
                .addColumnString("schema_version", ServiceNodeLinks.SCHEMA_VERSION)
                .addColumnString("node_links", JsonSerializer.serialize(serviceNodeLinks))
                .addParameterUUID("service_id", serviceId)
                .build();

        batch.add(boundStatement);
    }

    public List<UUID> getServiceIdsByNodeId(UUID nodeId) throws RepositoryException {

        List<Row> rows = RepositoryHelper.getRowsById(
                session,
                statementCache,
                SERVICE_TO_NODE_TABLE,
                "node_id",
                nodeId);

        return rows.stream()
                .map(r -> r.getUUID("service_id"))
                .collect(Collectors.toList());
    }

    public ServiceNodeLinks getNodesByServiceId(UUID serviceId) throws RepositoryException {

        Row row = RepositoryHelper.getSingleRowFromId(
                session,
                statementCache,
                SERVICE_TO_NODE_TABLE,
                "service_id",
                serviceId);

        return convertRowToServiceNodeLinks(row);
    }

    private ServiceNodeLinks convertRowToServiceNodeLinks(Row row) throws RepositoryException {
        if (row == null)
            return null;

        try {
            return JsonSerializer.deserialize(ServiceNodeLinks.class, row.getString("node_links"));
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    public List<AuditItem<ServiceNodeLinks>> getAuditHistory(UUID serviceId) throws RepositoryException {
        try {
            return AuditHelper.getAuditItems(session, statementCache, SERVICE_TO_NODE_TABLE, serviceId, ServiceNodeLinks.class);
        } catch (DeserializationException e) {
            throw new RepositoryException(e);
        }
    }

    private void addAuditToBatch(
            BatchStatement batch,
            UUID serviceId,
            ServiceNodeLinks links,
            UUID userId,
            AuditMode mode) throws JsonProcessingException {

        BoundStatement boundStatement = AuditHelper.buildAuditStatement(
                statementCache,
                SERVICE_TO_NODE_TABLE,
                serviceId,
                userId,
                mode,
                ServiceNodeLinks.SCHEMA_VERSION,
                JsonSerializer.serialize(links));

        batch.add(boundStatement);
    }
}
