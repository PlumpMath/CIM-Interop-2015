package org.endeavourhealth.cim.common.configuration.nodes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public abstract class Node {
    private UUID id;

    @JsonIgnore
    public abstract NodePurpose getPurpose();

    @JsonIgnore
    public abstract String getSchemaVersion();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
