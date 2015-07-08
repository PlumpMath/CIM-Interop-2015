package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets.attributes;

public class ProblemStatusAttribute {
    private boolean active;
    private boolean past;

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
