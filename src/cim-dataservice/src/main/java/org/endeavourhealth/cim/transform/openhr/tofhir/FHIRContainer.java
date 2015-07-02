package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.hl7.fhir.instance.model.*;

import java.util.HashMap;
import java.util.Map;

public class FHIRContainer {
    private Patient patient;
    private Map<String, Organization> organisations;
    private Map<String, Location> locations;
    private Map<String, Practitioner> practitioners;
    private Map<String, Encounter> encounters;
    private Map<String, Resource> clinicalResources;
    private Map<String, String> eventEncouterMap;

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

    public Map<String, Location> getLocations() {
        if (locations == null) locations = new HashMap<>();
        return locations;
    }

    public void setLocations(Map<String, Location> locations) { this.locations = locations; }

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

    public Map<String, String> getEventEncouterMap() {
        if (eventEncouterMap == null) eventEncouterMap = new HashMap<>();
        return eventEncouterMap;
    }

    public void setEventEncouterMap(Map<String, String> eventEncouterMap) {
        this.eventEncouterMap = eventEncouterMap;
    }
}
