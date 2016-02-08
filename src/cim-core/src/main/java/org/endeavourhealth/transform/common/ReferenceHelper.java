package org.endeavourhealth.transform.common;

import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.ResourceType;
import org.endeavourhealth.core.text.TextUtils;

public class ReferenceHelper {
    public static String createResourceReference(ResourceType resourceType, String id) {
        return resourceType.toString() + "/" + id;
    }

    public static Reference createReference(ResourceType resourceType, String id) {
        return new Reference().setReference(createResourceReference(resourceType, id));
    }

    public static Reference createInternalReference(String id) {
        return new Reference().setReference("#" + id);
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

    public static Boolean referenceEquals(Reference reference, ResourceType resourceType, String id) {
        if (TextUtils.isNullOrTrimmedEmpty(id))
            return false;

        String referenceId = getReferenceId(reference, resourceType);

        return id.equals(referenceId);
    }
}
