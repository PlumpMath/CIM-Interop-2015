package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.NameConverter;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleTransformer {

    public static ArrayList<Resource> transformToScheduleResources(AppointmentSessionList appointmentSessionList) throws SerializationException, TransformFeatureNotSupportedException {
        ArrayList<Practitioner> practitioners = new ArrayList<Practitioner>();
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();

        for (AppointmentSessionStruct appointmentSession : appointmentSessionList.getAppointmentSession()) {
            Practitioner practitioner = transformToPractitioner(appointmentSession.getHolderList());

            if (!practitioners
                    .stream()
                    .anyMatch(t -> (t.getId().equals(practitioner.getId())))) {
                practitioners.add(practitioner);
            }

            schedules.add(transformToSchedule(appointmentSession, practitioner));
        }

        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.addAll(practitioners);
        resources.addAll(schedules);

        return resources;
    }

    private static Schedule transformToSchedule(AppointmentSessionStruct appointmentSession, Practitioner practitioner) throws SerializationException, TransformFeatureNotSupportedException {
        Schedule schedule = new Schedule();
        schedule.setId(Integer.toString(appointmentSession.getDBID()));
        schedule.setComment(appointmentSession.getName());

        Reference reference = TransformHelper.createReference(Practitioner.class, practitioner.getId());

        schedule.setActor(reference);

        Period period = new Period();

        Date fromDate = EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getStartTime());
        Date toDate = EmisOpenCommon.getDateAndTime(appointmentSession.getDate(), appointmentSession.getEndTime());

        period.setStart(fromDate);
        period.setEnd(toDate);
        schedule.setPlanningHorizon(period);

        return schedule;
    }

    private static Practitioner transformToPractitioner(HolderList holderList) throws TransformFeatureNotSupportedException {
        List<HolderStruct> holders = holderList.getHolder();

        if (holders.size() != 1)
            throw new TransformFeatureNotSupportedException("AppointmentSession must have 1 holder.");

        HolderStruct holder = holders.get(0);

        Practitioner practitioner = new Practitioner();
        practitioner.setId(Integer.toString(holder.getDBID()));

        HumanName humanName = NameConverter.convert(holder.getFirstNames(), holder.getLastName(), holder.getTitle());
        practitioner.setName(humanName);

        return practitioner;
    }
}
