package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.ResourceType;

import java.util.Map;

public class ReferenceHelper {
    public static String createResourceReference(ResourceType resourceType, String id) {
        return resourceType.toString() + "/" + id;
    }

    public static Reference createReference(ResourceType resourceType, String id) {
        return new Reference().setReference(createResourceReference(resourceType, id));
    }

    public static String getReferenceId(Reference reference, ResourceType resourceType) {
        if (reference == null)
            return null;

        String[] parts = reference.getReference().split("\\/");

        if ((parts == null) || (parts.length == 0))
            return null;

        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid reference string.");

        if (!parts[0].equals(resourceType.toString()))
            return null;

        return parts[1];
    }

    public static void updateReferenceFromMap(Reference reference, ResourceType resourceType, Map<String, String> updateMap) {
        if (reference == null)
            return;

        String referenceId = getReferenceId(reference, resourceType);

        if (!TextUtils.isNullOrTrimmedEmpty(referenceId))
            if (updateMap.containsKey(referenceId))
                reference.setReference(ReferenceHelper.createResourceReference(resourceType, updateMap.get(referenceId)));

    }
}
