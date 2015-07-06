package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Resource;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BundleHelper {
    public static Bundle createBundle(String id, String namespace, ArrayList<Resource> resources) {
        Bundle bundle = new Bundle()
                .setType(Bundle.BundleType.COLLECTION)
                .setBase(namespace);

        if (!TextUtils.isNullOrTrimmedEmpty(id))
            bundle.setId(id);

        resources.forEach(t -> bundle.addEntry(new Bundle.BundleEntryComponent().setResource(t)));

        return bundle;
    }

    public static <T extends Resource> ArrayList<T> getResourcesOfType(Bundle bundle, Class<T> resourceType) {
        return bundle
                .getEntry()
                .stream()
                .filter(t -> t.getResource().getClass().equals(resourceType))
                .map(t -> (T)t.getResource())
                .collect(Collectors.toCollection(ArrayList<T>::new));
    }
}
