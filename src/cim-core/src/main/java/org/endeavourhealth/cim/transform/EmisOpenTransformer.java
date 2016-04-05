package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.emisopen.AppointmentTransformer;
import org.endeavourhealth.cim.transform.emisopen.ScheduleTransformer;
import org.endeavourhealth.cim.transform.emisopen.SlotTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.hl7.fhir.instance.model.*;

import java.util.List;

public class EmisOpenTransformer
{
    public List<Schedule> toFhirSchedules(String eopenSchedulesXml) throws TransformException
    {
        AppointmentSessionList appointmentSessionList = TransformHelper.unmarshall(eopenSchedulesXml, AppointmentSessionList.class);
        return ScheduleTransformer.transform(appointmentSessionList);
    }

    public List<Slot> toFhirSlots(String eopenSlotsXml) throws TransformException
    {
        SlotListStruct slotListStruct = TransformHelper.unmarshall(eopenSlotsXml, SlotListStruct.class);
        return SlotTransformer.transform(slotListStruct);
    }

    public List<Appointment> toFhirAppointments(String patientId, String eopenAppointmentsXml) throws TransformException
    {
        PatientAppointmentList patientAppointmentList = TransformHelper.unmarshall(eopenAppointmentsXml, PatientAppointmentList.class);
        return AppointmentTransformer.transform(patientId, patientAppointmentList);
    }
}
