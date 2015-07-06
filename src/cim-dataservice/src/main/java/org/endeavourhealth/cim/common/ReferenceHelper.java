package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Resource;

public class ReferenceHelper {
    public static <T extends Resource> String createResourceReference(Class<T> resourceClass, String id) {
        return resourceClass.getSimpleName() + "/" + id;
    }

    public static <T extends Resource> Reference createReference(Class<T> resourceClass, String id) {
        Reference reference = new Reference();
        reference.setReference(createResourceReference(resourceClass, id));
        return reference;
    }

    public static <T extends Resource> String getReferenceId(Reference reference, Class<T> resourceClass) {
        if (reference == null)
            return null;

        String[] parts = reference.getReference().split("\\/");

        if ((parts == null) || (parts.length == 0))
            return null;

        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid reference string.");

        if (!parts[0].equals(resourceClass.getSimpleName()))
            return null;

        return parts[1];
    }
}
