package org.endeavourhealth.cim.dataprotocols.admin.models;

import org.endeavourhealth.cim.common.models.Coding;

import java.util.List;
import java.util.UUID;

public class Organisation {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private List<Identifier> identifiers;
    private Coding type;
    private boolean active;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public Coding getType() {
        return type;
    }

    public void setType(Coding type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
