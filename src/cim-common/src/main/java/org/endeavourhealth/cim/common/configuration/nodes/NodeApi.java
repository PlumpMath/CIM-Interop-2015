package org.endeavourhealth.cim.common.configuration.nodes;

import org.endeavourhealth.cim.common.configuration.nodes.data.NodeRepository;
import org.endeavourhealth.cim.common.configuration.nodes.data.ServiceNodeLinkRepository;
import org.endeavourhealth.cim.common.configuration.nodes.models.InboundQueueNode;
import org.endeavourhealth.cim.common.configuration.nodes.models.Node;
import org.endeavourhealth.cim.common.configuration.nodes.models.NodePurpose;
import org.endeavourhealth.cim.common.configuration.nodes.models.ServiceNodeLinks;
import org.endeavourhealth.cim.common.data.RepositoryException;

import java.util.UUID;

public class NodeApi {

    public static InboundQueueNode GetServicesInboundQueueConfig(UUID serviceId) throws InternalProcessingException {

        try {

            ServiceNodeLinkRepository serviceNodeLinkRepository = new ServiceNodeLinkRepository();
            ServiceNodeLinks links = serviceNodeLinkRepository.getNodesByServiceId(serviceId);

            if (links == null)
                return null;

            ServiceNodeLinks.ServiceNodeLink nodeLink = links.find(NodePurpose.InboundQueue);

            NodeRepository nodeRepository = new NodeRepository();
            Node rawNode = nodeRepository.getById(nodeLink.getNodePurpose(), nodeLink.getNodeId());

            if (rawNode == null)
                throw new RepositoryException("Node not found.  NodePurpose: " + nodeLink.getNodePurpose().toString() + " NodeId: " + nodeLink.getNodeId().toString());

            return (InboundQueueNode) rawNode;

        } catch (Exception e) {
            throw new InternalProcessingException("Exception thrown during processing service ID: " + serviceId.toString(), e);
        }
    }
}
