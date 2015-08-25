package org.endeavourhealth.cim.transform.common;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Meta;
import org.hl7.fhir.instance.model.Resource;
import org.endeavourhealth.cim.common.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BundleHelper {

    public static Bundle createBundle(BundleProperties bundleProperties, List<Resource> resources) {

        return createBundle(bundleProperties.getBundleType(), bundleProperties.getBundleId(), bundleProperties.getBaseUri(), resources);
    }

    public static Bundle createBundle(Bundle.BundleType bundleType, String id, String baseUri, List<Resource> resources) {

        return createBundle(bundleType, id, baseUri, null, resources);
    }

    public static Bundle createBundle(Bundle.BundleType bundleType, String id, String baseUri, Date lastUpdated, List<Resource> resources) {

        Bundle bundle = new Bundle()
            .setType(bundleType)
            .setBase(baseUri);

        if (!TextUtils.isNullOrTrimmedEmpty(id))
            bundle.setId(id);

        if (lastUpdated != null)
            bundle.setMeta(new Meta().setLastUpdated(lastUpdated));

        resources.forEach(t -> bundle.addEntry(new Bundle.BundleEntryComponent().setResource(t)));

        bundle.setTotal(resources.size());

        return bundle;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Resource> List<T> getResourcesOfType(Bundle bundle, Class<T> resourceType) {
        return bundle
                .getEntry()
                .stream()
                .filter(t -> t.getResource().getClass().equals(resourceType))
                .map(t -> (T)t.getResource())
                .collect(Collectors.toCollection(ArrayList<T>::new));
    }
}
