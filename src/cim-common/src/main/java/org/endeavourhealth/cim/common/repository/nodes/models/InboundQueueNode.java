package org.endeavourhealth.cim.common.repository.nodes.models;

public class InboundQueueNode extends Node {

    private String ipAddress;
    private String queueName;

    @Override
    public NodePurpose getPurpose() {
        return NodePurpose.InboundQueue;
    }

    @Override
    public String getSchemaVersion() {
        return "1.0";
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }


}
