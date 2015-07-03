package org.endeavourhealth.cim.transform.emisopen.tofhir.admin;

import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.AppointmentStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentTransformer {

    public static ArrayList<Resource> transformToAppointmentResources(PatientAppointmentList patientAppointmentList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        for (AppointmentStruct appointment : patientAppointmentList.getAppointment()) {
            appointments.add(transformToAppointment(appointment));
        }

        ArrayList<Resource> resources = new ArrayList<Resource>(appointments);

        return resources;
    }

    public static Appointment transformToAppointment(AppointmentStruct appointmentStruct) throws SerializationException {
        Appointment appointment = new Appointment();

        appointment.setId(Integer.toString(appointmentStruct.getSlotID()));
        appointment.setStatus(getAppointmentStatus(appointmentStruct.getStatus()));

        if (!TextUtils.isNullOrTrimmedEmpty(appointmentStruct.getReason())) {
            CodeableConcept codeableConcept = new CodeableConcept();
            codeableConcept.setText(appointmentStruct.getReason());

            appointment.setReason(codeableConcept);
        }

        Date startTime = EmisOpenCommon.getDateAndTime(appointmentStruct.getDate(), appointmentStruct.getStartTime());
        appointment.setStart(startTime);

        Date endTime = EmisOpenCommon.addMinutesToTime(startTime, Integer.parseInt(appointmentStruct.getDuration()));
        appointment.setEnd(endTime);

        Reference reference = new Reference();
        reference.setReference(TransformHelper.createResourceReference(Slot.class, Integer.toString(appointmentStruct.getSlotID())));
        appointment.addSlot(reference);

        return appointment;
    }

    private static Appointment.Appointmentstatus getAppointmentStatus(String status) {
        switch (status) {
            case "Slot Available": return Appointment.Appointmentstatus.BOOKED;

            case "Start Call":
            case "Quiet Send In":
            case "Send In":
            case "Arrived": return Appointment.Appointmentstatus.ARRIVED;

            case "Cannot Be Seen":
            case "DNA":
            case "Telephone - Not In":
            case "Walked Out":
            case "Visited - Not In": return Appointment.Appointmentstatus.NOSHOW;

            case "Left":
            case "Telephone - Complete":
            case "Visited": return Appointment.Appointmentstatus.FULFILLED;

            case "Unknown":
            default: return Appointment.Appointmentstatus.NULL;
        }
    }
}
