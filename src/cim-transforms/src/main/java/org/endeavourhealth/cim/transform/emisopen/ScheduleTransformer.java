package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.common.FHIRConstants;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.NameConverter;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.*;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleTransformer {

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
            Reference reference = ReferenceHelper.createReference(ResourceType.Practitioner, practitioners.get(i).getId());

            if (i == 0) {
                schedule.setActor(reference);
            } else {
                schedule.addExtension(new Extension()
                        .setUrl(FHIRConstants.SCHEDULEADDITIONALACTOR_EXTENSION_URL)
                        .setValue(reference));
            }
        }

        schedule.addExtension(new Extension()
                .setUrl(FHIRConstants.SCHEDULEADDITIONALACTOR_EXTENSION_URL)
                .setValue(ReferenceHelper.createReference(ResourceType.Location, location.getId())));

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
        updateScheduleActorIds(schedules, ResourceType.Practitioner, userIdGuidMap);

        Map<String, String> locationIdGuidMap = EmisOpenCommon.buildLocationIdGuidMap(organisationInformation);
        updateScheduleActorIds(schedules, ResourceType.Location, locationIdGuidMap);
    }

    private static void updateScheduleActorIds(List<Schedule> schedules, ResourceType actorResourceType, Map<String, String> idGuidMap) {
        for (Schedule schedule : schedules) {
            String id = ReferenceHelper.getReferenceId(schedule.getActor(), actorResourceType);

            if (!TextUtils.isNullOrTrimmedEmpty(id))
                if (idGuidMap.containsKey(id))
                    schedule.setActor(ReferenceHelper.createReference(actorResourceType, idGuidMap.get(id)));

            for (Extension extension : schedule.getExtension()) {
                if (FHIRConstants.SCHEDULEADDITIONALACTOR_EXTENSION_URL.equals(extension.getUrl())) {
                    if (extension.getValue() instanceof Reference) {

                    String id2 = ReferenceHelper.getReferenceId((Reference)extension.getValue(), actorResourceType);

                    if (!TextUtils.isNullOrTrimmedEmpty(id2))
                        if (idGuidMap.containsKey(id2))
                            extension.setValue(ReferenceHelper.createReference(actorResourceType, idGuidMap.get(id2)));
                    }
                }
            }
        }
    }
}
