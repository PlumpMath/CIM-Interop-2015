package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.hl7.fhir.instance.model.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FHIRContainer {
    private Patient patient;
    private Map<String, Resource> adminResources;
    private Map<String, Resource> clinicalResources;
    private Map<String, String> eventEncounterMap;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<String, Resource> getAdminResources() {
        if (adminResources == null) adminResources = new HashMap<>();
        return adminResources;
    }

    public void setAdminResources(Map<String, Resource> adminResources) {
        this.adminResources = adminResources;
    }

    public Map<String, Resource> getClinicalResources() {
        if (clinicalResources == null) clinicalResources = new HashMap<>();
        return clinicalResources;
    }

    public void setClinicalResources(Map<String, Resource> clinicalResources) {
        this.clinicalResources = clinicalResources;
    }

    public Map<String, String> getEventEncounterMap() {
        if (eventEncounterMap == null) eventEncounterMap = new HashMap<>();
        return eventEncounterMap;
    }

    public void setEventEncounterMap(Map<String, String> eventEncounterMap) {
        this.eventEncounterMap = eventEncounterMap;
    }

    public List<Resource> getSortedAdminResources() {
        return getAdminResources().values()
                .stream()
                .sorted(new ResourceComparator())
                .collect(Collectors.toList());
    }

    public List<Resource> getSortedClinicalResources() {
        return getClinicalResources().values()
                .stream()
                .sorted(new ResourceComparator())
                .collect(Collectors.toList());
    }

    private static class ResourceComparator implements Comparator<Resource>
    {
        public int compare(Resource r1, Resource r2)
        {
            return r1.getResourceType().toString().compareTo(r2.getResourceType().toString());
        }
    }
}