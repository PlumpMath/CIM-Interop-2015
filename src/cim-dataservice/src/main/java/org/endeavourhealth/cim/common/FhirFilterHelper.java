package org.endeavourhealth.cim.common;

import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.UUID;

public class FhirFilterHelper {
	public static Bundle getConditions(Bundle bundle) {
		// Apply condition filter (in case supplier can only provide full record)
//		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
//			if (component.getResource() instanceof Encounter) {
//				Encounter encounter = (Encounter)component.getResource();
//				if (encounter.)
//			}
//		}

		return bundle;
	}

	public static Bundle getImmunizations(Bundle bundle) {
		// Apply immunization filter (in case supplier can only provide full record)
//		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
//			if (component.getResource() instanceof Encounter) {
//				Encounter encounter = (Encounter)component.getResource();
//				if (encounter.)
//			}
//		}

		return bundle;
	}

	public static Bundle getMedicationPrescriptions(Bundle bundle) {
		// Apply medication prescription filter (in case supplier can only provide full record)
//		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
//			if (component.getResource() instanceof Encounter) {
//				Encounter encounter = (Encounter)component.getResource();
//				if (encounter.)
//			}
//		}

		return bundle;
	}

	public static Bundle getAllergyIntolerances(Bundle bundle) {
		// Apply allergy intolerance filter (in case supplier can only provide full record)
//		for(Bundle.BundleEntryComponent component : bundle.getEntry()) {
//			if (component.getResource() instanceof Encounter) {
//				Encounter encounter = (Encounter)component.getResource();
//				if (encounter.)
//			}
//		}

		return bundle;
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
						if (EmisOpenCommon.SCHEDULEADDITIONALACTOR_EXTENSION_URL.equals(extension.getUrl()))
							if (extension.getValue() instanceof Reference)
								if (ReferenceHelper.referenceEquals((Reference)extension.getValue(), ResourceType.Practitioner, practitionerId.toString()))
									resources.add(schedule);
				}
			}
		}

		return BundleHelper.createBundle(bundle.getType(), bundle.getId(), bundle.getBase(), resources);
	}
}
