package org.endeavourhealth.cim.common;

import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FhirFilterHelper {
	private static List<ResourceType> _adminResourceTypes = Arrays.asList(
			ResourceType.Organization,
			ResourceType.Location,
			ResourceType.Practitioner,
			ResourceType.Patient
			);

	public static Bundle getConditions(Bundle bundle) {
		return removeNonAdminResourcesExcept(bundle, ResourceType.Condition);
	}

	public static Bundle getImmunizations(Bundle bundle) {
		return removeNonAdminResourcesExcept(bundle, ResourceType.Immunization);
	}

	public static Bundle getMedicationPrescriptions(Bundle bundle) {
		return removeNonAdminResourcesExcept(bundle, ResourceType.MedicationPrescription);
	}

	public static Bundle getAllergyIntolerances(Bundle bundle) {
		return removeNonAdminResourcesExcept(bundle, ResourceType.AllergyIntolerance);
	}

	public static Bundle filterScheduleByPractitioner(Bundle bundle, UUID practitionerId) {
		if (practitionerId == null)
			return bundle;

		ArrayList<Resource> resources = new ArrayList<>();

		for (Bundle.BundleEntryComponent bundleEntryComponent : bundle.getEntry()) {
			if (bundleEntryComponent.getResource() instanceof Schedule) {
				Schedule schedule = (Schedule) bundleEntryComponent.getResource();

				if (ReferenceHelper.referenceEquals(schedule.getActor(), ResourceType.Practitioner, practitionerId.toString())) {
					resources.add(schedule);
				}
				else {
					for (Extension extension : schedule.getExtension())
						if (FHIRConstants.SCHEDULEADDITIONALACTOR_EXTENSION_URL.equals(extension.getUrl()))
							if (extension.getValue() instanceof Reference)
								if (ReferenceHelper.referenceEquals((Reference)extension.getValue(), ResourceType.Practitioner, practitionerId.toString()))
									resources.add(schedule);
				}
			}
		}

		return BundleHelper.createBundle(bundle.getType(), bundle.getId(), bundle.getBase(), resources);
	}

	private static boolean isAdminResource(Resource resource) {
		return _adminResourceTypes.contains(resource.getResourceType());
	}

	public static Bundle removeNonAdminResourcesExcept(Bundle bundle, ResourceType resourceTypeToKeep) {
		List<Bundle.BundleEntryComponent> componentsToRemove = new ArrayList<>();

		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
			Resource resource = component.getResource();
			if (isAdminResource(resource) == false && resource.getResourceType() != resourceTypeToKeep)
				componentsToRemove.add(component);
		}

		bundle.getEntry().removeAll(componentsToRemove);

		return bundle;
	}
}
