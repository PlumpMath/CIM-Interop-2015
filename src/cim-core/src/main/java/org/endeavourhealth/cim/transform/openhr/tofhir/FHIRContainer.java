package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.hl7.fhir.instance.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class FhirContainer
{
    private final List<Resource> _resources = new ArrayList<>();
    private final Map<String, Resource> _resourceMap = new HashMap<>();

    public <T extends Resource> void addResources(List<T> resources)
    {
        resources.forEach(t -> addResource(t));
    }

    public void addResource(Resource resource)
    {
        _resourceMap.put(resource.getId(), resource);
        _resources.add(resource);
    }

    public List<Resource> getResources()
    {
        return _resources;
    }

    public Resource getResourceById(String id)
    {
        return _resourceMap.get(id);
    }

    public <T extends Resource> List<T> getResourcesOfType(Class<T> resourceType)
    {
        return _resources
                .stream()
                .filter(resourceType::isInstance)
                .map(resourceType::cast)
                .collect(Collectors.toList());
    }
}