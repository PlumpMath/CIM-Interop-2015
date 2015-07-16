package org.endeavourhealth.cim.common;

import org.apache.camel.util.ResourceHelper;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Resource;
import org.hl7.fhir.instance.model.Schedule;

import java.util.List;

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

	public static Bundle filterScheduleByPractitioner(Bundle bundle, String practitionerId) {
		return bundle;
	}
}
