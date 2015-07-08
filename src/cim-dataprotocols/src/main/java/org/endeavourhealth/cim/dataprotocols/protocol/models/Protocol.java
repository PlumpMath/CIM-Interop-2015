package org.endeavourhealth.cim.dataprotocols.protocol.models;

import java.util.List;
import java.util.UUID;

/**
 * A Data Distribution Protocol describes a set of configuration attributes,
 * the values of which are used when operating data flows within the data service.
 *
 * A DDP type is usually based the national data distribution rules as produced by the
 * IG toolkit. However, local protocols covering data distribution needs that have not
 * yet been determined nationally will also exist.
 *
 * A DDP is usually, but not always named in line with the overall healthcare data processes
 * which it supports. A DDP is matched closely to the purpose of the data use, the nature
 * of the data that is exchanged, the method by which it is exchanged, and the exchange schedule.
 * The naming of a DDP is in general, likely to be by national consensus and derived from the
 * data set that supports the relevant process. Examples of DDP types may be Urgent care,
 * Pathology reporting, General Practice, Specialist care, diabetes care, Clinical reports,
 * Combined general and specialist care.
 */
public class Protocol {
    public static final String SCHEMA_VERSION = "1.0";

    public enum PatientConsentModel {
        Explicit, // Explicit consent required.
        Implicit // Implicit consent.
    }

    private UUID id;
    private String name;
    private String description;
    private boolean enabled;
    private PatientConsentModel patientConsent;
    private UUID derivedFromId;
    private UUID dataSetCollectionId;
    private List<Contract> publisherContracts;
    private List<Contract> subscriberContracts;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PatientConsentModel getPatientConsent() {
        return patientConsent;
    }

    public void setPatientConsent(PatientConsentModel patientConsent) {
        this.patientConsent = patientConsent;
    }

    public UUID getDerivedFromId() {
        return derivedFromId;
    }

    public void setDerivedFromId(UUID derivedFromId) {
        this.derivedFromId = derivedFromId;
    }

    public UUID getDataSetCollectionId() {
        return dataSetCollectionId;
    }

    public void setDataSetCollectionId(UUID dataSetCollectionId) {
        this.dataSetCollectionId = dataSetCollectionId;
    }

    public List<Contract> getPublisherContracts() {
        return publisherContracts;
    }

    public void setPublisherContracts(List<Contract> publisherContracts) {
        this.publisherContracts = publisherContracts;
    }

    public List<Contract> getSubscriberContracts() {
        return subscriberContracts;
    }

    public void setSubscriberContracts(List<Contract> subscriberContracts) {
        this.subscriberContracts = subscriberContracts;
    }
}
