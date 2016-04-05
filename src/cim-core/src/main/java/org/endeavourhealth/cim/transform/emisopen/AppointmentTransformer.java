package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.AppointmentStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.HolderStruct;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppointmentTransformer
{
    public static List<Appointment> transform(String patientGuid, PatientAppointmentList patientAppointmentList) throws TransformException
    {
        List<Appointment> appointments = new ArrayList<Appointment>();

        for (AppointmentStruct appointment : patientAppointmentList.getAppointment())
            appointments.add(transformToAppointment(patientGuid, appointment));

        return appointments;
    }

    public static Appointment transformToAppointment(String patientGuid, AppointmentStruct appointmentStruct) throws TransformException
    {
        Appointment appointment = new Appointment();

        appointment.setId(appointmentStruct.getSlotGUID());
        appointment.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_APPOINTMENT));

        appointment.setStatus(getAppointmentStatus(appointmentStruct.getStatus()));

        if (!TextUtils.isNullOrTrimmedEmpty(appointmentStruct.getReason()))
        {
            CodeableConcept codeableConcept = new CodeableConcept();
            codeableConcept.setText(appointmentStruct.getReason());

            appointment.setReason(codeableConcept);
        }

        Date startTime = EmisOpenCommon.getDateAndTime(appointmentStruct.getDate(), appointmentStruct.getStartTime());
        appointment.setStart(startTime);

        Date endTime = EmisOpenCommon.addMinutesToTime(startTime, Integer.parseInt(appointmentStruct.getDuration()));
        appointment.setEnd(endTime);

        appointment.addSlot(ReferenceHelper.createReference(ResourceType.Slot, appointmentStruct.getSlotGUID()));

        Appointment.ParticipantRequired requiredStatus = Appointment.ParticipantRequired.REQUIRED;
        Appointment.ParticipationStatus participationstatus = Appointment.ParticipationStatus.ACCEPTED;

        appointment.addParticipant(createParticipant(ResourceType.Patient, patientGuid, requiredStatus, participationstatus));

        for (HolderStruct holder : appointmentStruct.getHolderList().getHolder())
            appointment.addParticipant(createParticipant(ResourceType.Practitioner, holder.getGUID(), requiredStatus, participationstatus));

        appointment.addParticipant(createParticipant(ResourceType.Location, appointmentStruct.getSiteGUID(), requiredStatus, participationstatus));

        return appointment;
    }

    private static Appointment.AppointmentParticipantComponent createParticipant(ResourceType resourceType, String id, Appointment.ParticipantRequired required, Appointment.ParticipationStatus status)
    {
        return new Appointment.AppointmentParticipantComponent()
                .setActor(ReferenceHelper.createReference(resourceType, id))
                .setRequired(required)
                .setStatus(status);
    }

    private static Appointment.AppointmentStatus getAppointmentStatus(String status)
    {
        switch (status) {
            case "Slot Available":
            case "Booked": return Appointment.AppointmentStatus.BOOKED;

            case "Start Call":
            case "Quiet Send In":
            case "Send In":
            case "Arrived": return Appointment.AppointmentStatus.ARRIVED;

            case "Cannot Be Seen":
            case "DNA":
            case "Telephone - Not In":
            case "Walked Out":
            case "Visited - Not In": return Appointment.AppointmentStatus.NOSHOW;

            case "Left":
            case "Telephone - Complete":
            case "Visited": return Appointment.AppointmentStatus.FULFILLED;

            case "Unknown":
            default: return Appointment.AppointmentStatus.NULL;
        }
    }
}
