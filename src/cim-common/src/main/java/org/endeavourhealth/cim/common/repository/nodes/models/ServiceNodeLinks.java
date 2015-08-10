package org.endeavourhealth.cim.common.repository.nodes.models;

import org.endeavourhealth.cim.common.stream.StreamExtension;

import java.util.ArrayList;
import java.util.UUID;

public class ServiceNodeLinks {
    public static final String SCHEMA_VERSION = "1.0";

    public static class ServiceNodeLink {
        private NodePurpose nodePurpose;
        private UUID nodeId;

        public ServiceNodeLink(NodePurpose nodePurpose, UUID nodeId) {
            setNodePurpose(nodePurpose);
            setNodeId(nodeId);
        }

        public ServiceNodeLink() {

        }

        public NodePurpose getNodePurpose() {
            return nodePurpose;
        }

        public void setNodePurpose(NodePurpose nodePurpose) {
            this.nodePurpose = nodePurpose;
        }

        public UUID getNodeId() {
            return nodeId;
        }

        public void setNodeId(UUID nodeId) {
            this.nodeId = nodeId;
        }
    }

    private ArrayList<ServiceNodeLink> links = new ArrayList<ServiceNodeLink>();

    public ArrayList<ServiceNodeLink> getLinks() {
        return links;
    }

    public ServiceNodeLink find(NodePurpose purpose) {

        return links.stream()
            .filter(s -> s.getNodePurpose() == purpose)
            .collect(StreamExtension.singleOrNullCollector());
    }
}
