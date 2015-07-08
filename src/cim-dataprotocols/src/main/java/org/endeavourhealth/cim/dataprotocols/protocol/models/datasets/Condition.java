package org.endeavourhealth.cim.dataprotocols.protocol.models.datasets;

public abstract class Condition {
    private long id;
    private ConditionType type;

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
