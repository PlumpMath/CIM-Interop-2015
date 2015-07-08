package org.endeavourhealth.cim.dataprotocols.protocol.models;

import java.util.List;
import java.util.UUID;

public class System {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private String description;
    private boolean enabled;
    private List<TechnicalInterface> technicalInterfaces;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TechnicalInterface> getTechnicalInterfaces() {
        return technicalInterfaces;
    }

    public void setTechnicalInterfaces(List<TechnicalInterface> technicalInterfaces) {
        this.technicalInterfaces = technicalInterfaces;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
