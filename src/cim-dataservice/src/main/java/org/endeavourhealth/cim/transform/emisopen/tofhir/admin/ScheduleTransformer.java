package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.NameConverter;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.*;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleTransformer {

    public final static String SCHEDULEADDITIONALACTOR_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/Schedule/AdditionalActor";

    public static ArrayList<Resource> transformToScheduleResources(AppointmentSessionList appointmentSessionList, OrganisationInformation organisationInformation) throws SerializationException, TransformFeatureNotSupportedException {
        ArrayList<Practitioner> practitioners = new ArrayList<>();
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Schedule> schedules = new ArrayList<>();

        for (AppointmentSessionStruct appointmentSession : appointmentSessionList.getAppointmentSession()) {

            List<Practitioner> sessionPractitioners = transformToPractitioners(appointmentSession.getHolderList());
            practitioners.addAll(sessionPractitioners);

            Location location = transformToLocation(appointmentSession.getSite());
            locations.add(location);

            schedules.add(transformToSchedule(appointmentSession, location, sessionPractitioners));
        }

        updateScheduleActorIds(schedules, organisationInformation);

        ArrayList<Resource> resources = new ArrayList<Resource>();
//        resources.addAll(RemoveDuplicates(new ArrayList<Resource>(practitioners)));
//        resources.addAll(RemoveDuplicates(new ArrayList<Resource>(locations)));
        resources.addAll(schedules);

        return resources;
    }

    private static Schedule transformToSchedule(AppointmentSessionStruct appointmentSession, Location location, List<Practitioner> practitioners) throws SerializationException, TransformFeatureNotSupportedException {
        Schedule schedule = new Schedule();
        schedule.setId(Integer.toString(appointmentSession.getDBID()));

        Period period = new Period()
            .setStart(EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getStartTime()))
            .setEnd(EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getEndTime()));

        schedule
            .setPlanningHorizon(period)
            .setComment(appointmentSession.getName());

        for (int i = 0; i < practitioners.size(); i++) {
            Reference reference = ReferenceHelper.createReference(Practitioner.class, practitioners.get(i).getId());

            if (i == 0) {
                schedule.setActor(reference);
            } else {
                schedule.addExtension(new Extension()
                        .setUrl(SCHEDULEADDITIONALACTOR_EXTENSION_URL)
                        .setValue(reference));
            }
        }

        schedule.addExtension(new Extension()
                .setUrl(SCHEDULEADDITIONALACTOR_EXTENSION_URL)
                .setValue(ReferenceHelper.createReference(Location.class, location.getId())));

        return schedule;
    }

    private static Location transformToLocation(SiteStruct site) {
        return (Location)new Location()
                .setName(site.getName())
                .setId(Integer.toString(site.getDBID()));
    }

    private static List<Practitioner> transformToPractitioners(HolderList holders) {
        return holders
            .getHolder()
            .stream()
            .map(t -> transformToPractitioner(t))
            .collect(Collectors.toList());
    }

    private static Practitioner transformToPractitioner(HolderStruct holder) {
        return (Practitioner)new Practitioner()
            .setName(NameConverter.convert(holder.getFirstNames(), holder.getLastName(), holder.getTitle()))
            .setId(Integer.toString(holder.getDBID()));
    }

    private static List<Resource> RemoveDuplicatesById(List<Resource> resources) {
        return resources
                .stream()
                .filter(StreamExtension.distinctByKey(t -> t.getId()))
                .collect(Collectors.toList());
    }

    public static <T extends Resource> void updateScheduleActorIds(List<Schedule> schedules, OrganisationInformation organisationInformation) {
        Map<String, String> userIdGuidMap = EmisOpenCommon.buildUserIdGuidMap(organisationInformation);
        updateScheduleActorIds(schedules, Practitioner.class, userIdGuidMap);

        Map<String, String> locationIdGuidMap = EmisOpenCommon.buildLocationIdGuidMap(organisationInformation);
        updateScheduleActorIds(schedules, Location.class, locationIdGuidMap);
    }

    private static <T extends Resource> void updateScheduleActorIds(List<Schedule> schedules, Class<T> actorResourceType, Map<String, String> idGuidMap) {
        schedules.forEach(t -> ReferenceHelper.updateReferenceFromMap(t.getActor(), actorResourceType, idGuidMap));

        schedules.forEach(t ->
                t
                        .getExtension()
                        .stream()
                        .filter(s -> s.getUrl().equals(ScheduleTransformer.SCHEDULEADDITIONALACTOR_EXTENSION_URL))
                        .forEach(s -> ReferenceHelper.updateReferenceFromMap((Reference) s.getValue(), actorResourceType, idGuidMap)));
    }
}
