package org.endeavourhealth.cim.dataprotocols.protocol.models;

import java.util.UUID;

public class TechnicalInterface {
    private UUID id;
    private String description;
    private String messageType;
    private String messageFormat;
    private String messageFormatVersion;
    private Endpoint deliveryEndpoint;
    private Endpoint ackEndpoint;
    private boolean enabled;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessageFormatVersion() {
        return messageFormatVersion;
    }

    public void setMessageFormatVersion(String messageFormatVersion) {
        this.messageFormatVersion = messageFormatVersion;
    }

    public Endpoint getDeliveryEndpoint() {
        return deliveryEndpoint;
    }

    public void setDeliveryEndpoint(Endpoint deliveryEndpoint) {
        this.deliveryEndpoint = deliveryEndpoint;
    }

    public Endpoint getAckEndpoint() {
        return ackEndpoint;
    }

    public void setAckEndpoint(Endpoint ackEndpoint) {
        this.ackEndpoint = ackEndpoint;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
