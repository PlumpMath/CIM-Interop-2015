package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.ScheduleTransformer;
import org.hl7.fhir.instance.model.Appointment;
import org.hl7.fhir.instance.model.Practitioner;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class EmisAppointmentsDataHelper {
    public static ArrayList<String> getSchedulePractitionerIds(ArrayList<Schedule> schedules) {
        ArrayList<String> practitionerIds = new ArrayList<String>();

        practitionerIds.addAll(schedules
                .stream()
                .map(t -> ReferenceHelper.getReferenceId(t.getActor(), Practitioner.class))
                .filter(t -> !TextUtils.isNullOrTrimmedEmpty(t))
                .distinct()
                .collect(Collectors.toList()));

        schedules.forEach(t -> practitionerIds.addAll(
                        t
                            .getExtension()
                            .stream()
                            .filter(s -> s.getUrl().equals(ScheduleTransformer.SCHEDULEADDITIONALACTOR_EXTENSION_URL))
                            .map(s -> ReferenceHelper.getReferenceId(((Reference)s.getValue()), Practitioner.class))
                            .filter(s -> !TextUtils.isNullOrTrimmedEmpty(s))
                            .distinct()
                            .collect(Collectors.toList())
                        ));

        return practitionerIds;
    }

    public static void updateSchedulePractitionerIds(ArrayList<Schedule> schedules, HashMap<String, String> practitionerIdMap) {
        schedules.forEach(t -> ReferenceHelper.updateReferenceFromMap(t.getActor(), Practitioner.class, practitionerIdMap));

        schedules.forEach(t ->
                t
                    .getExtension()
                    .stream()
                    .filter(s -> s.getUrl().equals(ScheduleTransformer.SCHEDULEADDITIONALACTOR_EXTENSION_URL))
                    .forEach(s -> ReferenceHelper.updateReferenceFromMap((Reference)s.getValue(), Practitioner.class, practitionerIdMap)));
    }

    public static ArrayList<String> getAppointmentPractitionerIds(ArrayList<Appointment> appointments) {
        ArrayList<String> practitionerIds = new ArrayList<>();

        appointments
                .forEach(t -> practitionerIds.addAll(
                        t
                            .getParticipant()
                            .stream()
                            .map(s -> ReferenceHelper.getReferenceId(s.getActor(), Practitioner.class))
                            .filter(s -> !TextUtils.isNullOrTrimmedEmpty(s))
                            .distinct()
                            .collect(Collectors.toList())));

        return practitionerIds;
    }

    public static void updateAppointmentPractitionerIds(ArrayList<Appointment> appointments, HashMap<String, String> practitionerIdMap) {
        appointments.forEach(t ->
                t
                    .getParticipant()
                    .forEach(s -> ReferenceHelper.updateReferenceFromMap(s.getActor(), Practitioner.class, practitionerIdMap)));

    }
}
