package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.NameConverter;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.*;
import org.hl7.fhir.instance.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleTransformer
{
    private static SimpleDateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static ArrayList<Resource> transformToScheduleResources(AppointmentSessionList appointmentSessionList) throws SerializationException, TransformFeatureNotSupportedException
    {
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (AppointmentSessionStruct appointmentSession : appointmentSessionList.getAppointmentSession())
        {
            Practitioner practitioner = transformToPractitioner(appointmentSession.getHolderList());

            if (!resources
                    .stream()
                    .anyMatch(t -> (t instanceof Practitioner)
                                    && (t.getId().equals(practitioner.getId()))))
            {
                resources.add(practitioner);
            }

            Schedule schedule = transformToSchedule(appointmentSession, practitioner);
            resources.add(schedule);
        }

        return resources;
    }

    private static Schedule transformToSchedule(AppointmentSessionStruct appointmentSession, Practitioner practitioner) throws SerializationException, TransformFeatureNotSupportedException
    {
        Schedule schedule = new Schedule();
        schedule.setId(Integer.toString(appointmentSession.getDBID()));
        schedule.setComment(appointmentSession.getName());

        Reference reference = new Reference().setReference(TransformHelper.createResourceReference(Practitioner.class, practitioner.getId()));

        schedule.setActor(reference);

        Period period = new Period();

        Date fromDate = getDateAndTime(appointmentSession.getDate(), appointmentSession.getStartTime());
        Date toDate = getDateAndTime(appointmentSession.getDate(), appointmentSession.getEndTime());

        period.setStart(fromDate);
        period.setEnd(toDate);
        schedule.setPlanningHorizon(period);

        return schedule;
    }

    private static Practitioner transformToPractitioner(HolderList holderList) throws TransformFeatureNotSupportedException
    {
        List<HolderStruct> holders = holderList.getHolder();

        if (holders.size() != 1)
            throw new TransformFeatureNotSupportedException("AppointmentSession must have 1 holder.");

        HolderStruct holder = holders.get(0);

        Practitioner practitioner = new Practitioner();
        practitioner.setId(Integer.toString(holder.getDBID()));

        HumanName humanName = NameConverter.convert(holder.getTitle(), holder.getFirstNames(), holder.getLastName());
        practitioner.setName(humanName);

        return practitioner;
    }

    private static Date getDateAndTime(String dateString, String timeString) throws SerializationException
    {
        try
        {
            return _dateFormat.parse(dateString + " " + timeString);
        }
        catch (ParseException e)
        {
            throw new SerializationException("Could not parse date", e);
        }
    }
}
