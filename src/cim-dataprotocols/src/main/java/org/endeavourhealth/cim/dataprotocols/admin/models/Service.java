package org.endeavourhealth.cim.dataprotocols.admin.models;

//import org.endeavourhealth.dataservice.admin.models.ContactDetail;
import org.endeavourhealth.cim.common.models.Coding;

import java.util.List;
import java.util.UUID;

public class Service {
    public static final String SCHEMA_VERSION = "1.0";

    private UUID id;
    private String name;
    private String description;
    private Coding type;
    private List<Identifier> identifiers;
//    private List<ContactDetail> contactInfo;
    private List<UUID> organisations;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coding getType() {
        return type;
    }

    public void setType(Coding type) {
        this.type = type;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

//    public List<ContactDetail> getContactInfo() {
//        return contactInfo;
//    }

//    public void setContactInfo(List<ContactDetail> contactInfo) {
//        this.contactInfo = contactInfo;
//    }

    public List<UUID> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<UUID> organisations) {
        this.organisations = organisations;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
