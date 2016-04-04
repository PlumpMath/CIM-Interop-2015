package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleTransformer
{
    public static List<Schedule> transform(AppointmentSessionList appointmentSessionList) throws TransformException
    {
        ArrayList<Schedule> result = new ArrayList<>();

        for (AppointmentSessionStruct appointmentSession : appointmentSessionList.getAppointmentSession())
            result.add(transformToSchedule(appointmentSession));

        return result;
    }

    private static Schedule transformToSchedule(AppointmentSessionStruct appointmentSession) throws TransformException
    {
        Schedule schedule = new Schedule();

        schedule.setId(appointmentSession.getGUID());

        schedule.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_SCHEDULE));

        Period period = new Period()
            .setStart(EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getStartTime()))
            .setEnd(EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getEndTime()));

        schedule.setPlanningHorizon(period);

        HolderStruct firstPractitioner = appointmentSession.getHolderList().getHolder().stream().findFirst().orElse(null);

        if (firstPractitioner != null)
            schedule.setActor(ReferenceHelper.createReference(ResourceType.Practitioner, firstPractitioner.getGUID()));

        schedule.setComment(appointmentSession.getName());

        List<HolderStruct> subsequentPractitioners = appointmentSession.getHolderList().getHolder().stream().skip(1).collect(Collectors.toList());

        for (HolderStruct subsequentPractitioner : subsequentPractitioners)
            schedule.addExtension(new Extension()
                    .setUrl(FhirUris.EXTENSION_URI_ADDITIONALACTOREXTENSION)
                    .setValue(ReferenceHelper.createReference(ResourceType.Practitioner, subsequentPractitioner.getGUID())));

        if (appointmentSession.getSite() != null)
            schedule.addExtension(new Extension()
                    .setUrl(FhirUris.EXTENSION_URI_LOCATIONEXTENSION)
                    .setValue(ReferenceHelper.createReference(ResourceType.Location, appointmentSession.getSite().getGUID())));

        return schedule;
    }
}
