package org.endeavourhealth.cim.dataManager.emis;

import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FhirFilterHelper
{
	public static Bundle getConditions(Bundle bundle)
	{
		return BundleHelper.getResourcesOfType(bundle, ResourceType.Condition);
	}

	public static Bundle getImmunizations(Bundle bundle)
	{
		return BundleHelper.getResourcesOfType(bundle, ResourceType.Immunization);
	}

	public static Bundle getMedicationPrescriptions(Bundle bundle, MedicationOrder.MedicationOrderStatus medicationOrderStatus)
	{
		ArrayList<Resource> resources = new ArrayList<>();

		for (Bundle.BundleEntryComponent bundleEntryComponent : bundle.getEntry())	{

			if (bundleEntryComponent.getResource() instanceof MedicationOrder) {

				MedicationOrder medicationOrder = (MedicationOrder)bundleEntryComponent.getResource();

				boolean include = false;

				if (medicationOrderStatus == null)
					include = true;
				else if (medicationOrder.getStatus() != null)
					include = (medicationOrder.getStatus().equals(medicationOrderStatus));

  				if (include)
					resources.add(medicationOrder);
			}
		}

		return BundleHelper.createBundle(bundle.getType(), bundle.getId(), resources);
	}

	public static Bundle getAllergyIntolerances(Bundle bundle)
	{
		return BundleHelper.getResourcesOfType(bundle, ResourceType.AllergyIntolerance);
	}

	public static List<Schedule> filterScheduleByLocation(List<Schedule> schedules, UUID locationId)
	{
		List<Schedule> result = new ArrayList<>();

		if (locationId != null)
			for (Schedule schedule : schedules)
				for (Extension extension : schedule.getExtension())
					if (FhirUris.EXTENSION_URI_LOCATIONEXTENSION.equals(extension.getUrl()))
						if (extension.getValue() instanceof Reference)
							if (ReferenceHelper.referenceEquals((Reference)extension.getValue(), ResourceType.Location, locationId.toString()))
								result.add(schedule);

		return result;
	}

	public static List<Slot> removeBusySlots(List<Slot> slots)
	{
		return slots
				.stream()
				.filter(t -> t.getFreeBusyType() != Slot.SlotStatus.BUSY
						&& t.getFreeBusyType() != Slot.SlotStatus.BUSYTENTATIVE
						&& t.getFreeBusyType() != Slot.SlotStatus.BUSYUNAVAILABLE)
				.collect(Collectors.toList());
	}

	public static List<UUID> getScheduleIds(List<Schedule> schedules)
	{
		return schedules
				.stream()
				.map(t -> UUID.fromString(t.getId()))
				.collect(Collectors.toList());
	}

	public static List<Schedule> removeSchedulesWithNoSlots(List<Schedule> schedules, List<Slot> slots)
	{
		List<String> remainingScheduleIds = slots
				.stream()
				.map(t -> ReferenceHelper.getReferenceId(t.getSchedule(), ResourceType.Schedule))
				.distinct()
				.collect(Collectors.toList());

		return schedules
				.stream()
				.filter(t -> remainingScheduleIds.contains(t.getId()))
				.collect(Collectors.toList());
	}

	public static List<UUID> getPractitionerIds(List<Schedule> schedules)
	{
		List<UUID> result = new ArrayList<>();

		for (Schedule schedule : schedules)
		{
			Reference reference = schedule.getActor();

			if (reference != null)
				result.add(UUID.fromString(ReferenceHelper.getReferenceId(reference, ResourceType.Practitioner)));

			for (Extension extension : schedule.getExtension())
				if (FhirUris.EXTENSION_URI_ADDITIONALACTOREXTENSION.equals(extension.getUrl()))
					if (extension.getValue() instanceof Reference)
						result.add(UUID.fromString(ReferenceHelper.getReferenceId((Reference)extension.getValue(), ResourceType.Practitioner)));
		}

		return result
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}

	public static List<UUID> getLocationIds(List<Schedule> schedules)
	{
		List<UUID> result = new ArrayList<>();

		for (Schedule schedule : schedules)
			for (Extension extension : schedule.getExtension())
				if (FhirUris.EXTENSION_URI_LOCATIONEXTENSION.equals(extension.getUrl()))
					if (extension.getValue() instanceof Reference)
						result.add(UUID.fromString(ReferenceHelper.getReferenceId((Reference)extension.getValue(), ResourceType.Location)));

		return result
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}
}
