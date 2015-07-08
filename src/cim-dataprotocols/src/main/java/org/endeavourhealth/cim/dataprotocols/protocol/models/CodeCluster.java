package org.endeavourhealth.cim.dataprotocols.protocol.models;

import java.util.List;
import java.util.UUID;

public class CodeCluster {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private String description;
    private List<CodeClusterMember> members;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<CodeClusterMember> getMembers() { return members; }
    public void setMembers(List<CodeClusterMember> members) { this.members = members; }
}
