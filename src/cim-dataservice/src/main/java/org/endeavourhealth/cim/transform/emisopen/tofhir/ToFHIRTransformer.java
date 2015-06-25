package org.endeavourhealth.cim.transform.emisopen.tofhir;

import net.sf.saxon.expr.JPConverter;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.HolderList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.HolderStruct;
import org.hl7.fhir.instance.model.*;

import java.security.PrivilegedAction;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToFHIRTransformer
{
    private SimpleDateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Bundle transformToScheduleBundle(AppointmentSessionList appointmentSessionList) throws SerializationException, TransformFeatureNotSupportedException
    {
        Bundle bundle = new Bundle();

        for (AppointmentSessionStruct appointmentSession : appointmentSessionList.getAppointmentSession())
        {
            Practitioner practitioner = transformToPractitioner(appointmentSession.getHolderList());

            Schedule schedule = transformToSchedule(appointmentSession, practitioner);

            Bundle.BundleEntryComponent practitionerComponent = CreateBundleEntryComponent(practitioner);
            bundle.addEntry(practitionerComponent);

            Bundle.BundleEntryComponent scheduleComponent = CreateBundleEntryComponent(schedule);
            bundle.addEntry(scheduleComponent);
        }

        return bundle;
    }

    private Bundle.BundleEntryComponent CreateBundleEntryComponent(Resource resource)
    {
        Bundle.BundleEntryComponent bundleEntryComponent = new Bundle.BundleEntryComponent();
        bundleEntryComponent.setResource(resource);
        return bundleEntryComponent;
    }

    private Schedule transformToSchedule(AppointmentSessionStruct appointmentSession, Practitioner practitioner) throws SerializationException, TransformFeatureNotSupportedException
    {
        Schedule schedule = new Schedule();
        schedule.setId(Integer.toString(appointmentSession.getDBID()));

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

    private Practitioner transformToPractitioner(HolderList holderList) throws TransformFeatureNotSupportedException
    {
        List<HolderStruct> holders = holderList.getHolder();

        if (holders.size() != 1)
            throw new TransformFeatureNotSupportedException("AppointmentSession must have 1 holder.");

        HolderStruct holder = holders.get(0);

        Practitioner practitioner = new Practitioner();
        practitioner.setId(Integer.toString(holder.getDBID()));

        HumanName humanName = new HumanName();
        humanName.addPrefix(holder.getTitle());
        humanName.addGiven(holder.getFirstNames());
        humanName.addFamily(holder.getLastName());

        practitioner.setName(humanName);

        return practitioner;
    }

    private Date getDateAndTime(String dateString, String timeString) throws SerializationException
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
