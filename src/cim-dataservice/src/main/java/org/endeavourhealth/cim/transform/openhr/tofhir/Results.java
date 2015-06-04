package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.hl7.fhir.instance.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Results {
    private Patient patient;
    private Map<String, Organization> organisations;
    private Map<String, Practitioner> practitioners;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<String, Organization> getOrganisations() {
        if (organisations == null) {
            organisations = new HashMap<>();
        }
        return organisations;
    }

    public void setOrganisations(Map<String, Organization> organisations) {
        this.organisations = organisations;
    }

    public Map<String, Practitioner> getPractitioners() {
        if (practitioners == null) {
            practitioners = new HashMap<>();
        }
        return practitioners;
    }

    public void setPractitioners(Map<String, Practitioner> practitioners) {
        this.practitioners = practitioners;
    }
}
