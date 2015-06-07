package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.hl7.fhir.instance.model.*;

import java.util.HashMap;
import java.util.Map;

public class FHIRContainer {
    private Patient patient;
    private Map<String, Organization> organisations;
    private Map<String, Practitioner> practitioners;
    private Map<String, Encounter> encounters;
    private Map<String, Resource> clinicalResources;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<String, Organization> getOrganisations() {
        if (organisations == null) organisations = new HashMap<>();
        return organisations;
    }

    public void setOrganisations(Map<String, Organization> organisations) {
        this.organisations = organisations;
    }

    public Map<String, Practitioner> getPractitioners() {
        if (practitioners == null) practitioners = new HashMap<>();
        return practitioners;
    }

    public void setPractitioners(Map<String, Practitioner> practitioners) {
        this.practitioners = practitioners;
    }

    public Map<String, Encounter> getEncounters() {
        if (encounters == null) encounters = new HashMap<>();
        return encounters;
    }

    public void setEncounters(Map<String, Encounter> encounters) {
        this.encounters = encounters;
    }

    public Map<String, Resource> getClinicalResources() {
        if (clinicalResources == null) clinicalResources = new HashMap<>();
        return clinicalResources;
    }

    public void setClinicalResources(Map<String, Resource> clinicalResources) {
        this.clinicalResources = clinicalResources;
    }
}
