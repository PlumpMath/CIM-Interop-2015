package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.emisopen.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.AppointmentTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.ObjectFactory;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.hl7.fhir.instance.model.Bundle;

import java.text.SimpleDateFormat;

public class EmisOpenTransformer {

    public Bundle toFHIRAppointmentBundle(String patientGuid, String appointmentsXml, String organisationXml) throws TransformException {
        PatientAppointmentList appointmentList = TransformHelper.unmarshall(appointmentsXml, PatientAppointmentList.class);
        OrganisationInformation organisationInformation = TransformHelper.unmarshall(organisationXml, OrganisationInformation.class);

        ToFHIRTransformer toFHIRTransformer = new ToFHIRTransformer();
        return toFHIRTransformer.transformToAppointmentBundle(patientGuid, appointmentList, organisationInformation);
    }

    public Bundle toFHIRScheduleBundle(String schedulesXml, String organisationXml) throws TransformException {
        AppointmentSessionList appointmentSessionList = TransformHelper.unmarshall(schedulesXml, AppointmentSessionList.class);
        OrganisationInformation organisationInformation = TransformHelper.unmarshall(organisationXml, OrganisationInformation.class);

        ToFHIRTransformer toFHIRTransformer = new ToFHIRTransformer();
        return toFHIRTransformer.transformToScheduleBundle(appointmentSessionList, organisationInformation);
    }

    public Bundle toFHIRSlotBundle(String scheduleId, String slotsXml) throws TransformException {
        SlotListStruct slotListStruct = TransformHelper.unmarshall(slotsXml, SlotListStruct.class);

        ToFHIRTransformer toFHIRTransformer = new ToFHIRTransformer();
        return toFHIRTransformer.transformToSlotBundle(scheduleId, slotListStruct);
    }
}
