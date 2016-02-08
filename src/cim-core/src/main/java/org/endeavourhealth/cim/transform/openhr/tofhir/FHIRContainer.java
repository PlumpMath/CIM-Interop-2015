package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.hl7.fhir.instance.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class FHIRContainer {
    private final List<Resource> _resources = new ArrayList<>();
    private final Map<String, Resource> _resourceMap = new HashMap<>();
    private final Map<String, OpenHR001Encounter> _eventEncounterMap = new HashMap<>();

    public void addResource(Resource resource) {
        _resourceMap.put(resource.getId(), resource);
        _resources.add(resource);
    }

    public void addEventEncounterMap(String eventId, OpenHR001Encounter encounter) {
        _eventEncounterMap.putIfAbsent(eventId, encounter);
    }

    public List<Resource> getResources() {
        return _resources;
    }

    public Resource getResourceById(String id) {
        return _resourceMap.get(id);
    }

    public OpenHR001Encounter getEncounterFromEventId(String eventId) {
        return _eventEncounterMap.get(eventId);
    }

    public <T extends Resource> List<T> getResourcesOfType(Class<T> resourceType) {
        return _resources
                .stream()
                .filter(resourceType::isInstance)
                .map(resourceType::cast)
                .collect(Collectors.toList());
    }
}