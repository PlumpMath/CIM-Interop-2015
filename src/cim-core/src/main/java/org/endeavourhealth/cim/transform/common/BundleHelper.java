package org.endeavourhealth.cim.transform.common;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Meta;
import org.hl7.fhir.instance.model.Resource;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.hl7.fhir.instance.model.ResourceType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BundleHelper {

    public static <T extends Resource> Bundle createBundle(BundleProperties bundleProperties, List<T> resources) {

        return createBundle(bundleProperties.getBundleType(), bundleProperties.getBundleId(), resources);
    }

    public static <T extends Resource> Bundle createBundle(Bundle.BundleType bundleType, String id, List<T> resources) {

        return createBundle(bundleType, id, null, resources);
    }

    public static <T extends Resource> Bundle createBundle(Bundle.BundleType bundleType, String id, Date lastUpdated, List<T> resources) {

        Bundle bundle = new Bundle()
            .setType(bundleType);

        //TODO: Consider populating the entry.fullUrl element

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

    public static Bundle getResourcesOfType(Bundle bundle, ResourceType resourceType)
    {
		List<Resource> resources = new ArrayList<>();

		for(Bundle.BundleEntryComponent component : bundle.getEntry())
			if (component.getResource().getResourceType() == resourceType)
				resources.add(component.getResource());

		return BundleHelper.createBundle(bundle.getType(), bundle.getId(), resources);
	}

}
