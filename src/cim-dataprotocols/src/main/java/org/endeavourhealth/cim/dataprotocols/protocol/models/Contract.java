package org.endeavourhealth.cim.dataprotocols.protocol.models;

import java.util.UUID;


public class Contract {
    private UUID serviceId;
    private UUID systemId;
    private UUID technicalInterfaceId;
    private boolean active;

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getSystemId() {
        return systemId;
    }

    public void setSystemId(UUID systemId) {
        this.systemId = systemId;
    }

    public UUID getTechnicalInterfaceId() {
        return technicalInterfaceId;
    }

    public void setTechnicalInterfaceId(UUID technicalInterfaceId) {
        this.technicalInterfaceId = technicalInterfaceId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
