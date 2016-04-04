package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.emisopen.AppointmentTransformer;
import org.endeavourhealth.cim.transform.emisopen.ScheduleTransformer;
import org.endeavourhealth.cim.transform.emisopen.SlotTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
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

    public Bundle toFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException
    {
        PatientAppointmentList patientAppointmentList = TransformHelper.unmarshall(eopenAppointmentsXml, PatientAppointmentList.class);
        OrganisationInformation organisationInformation = TransformHelper.unmarshall(organisationXml, OrganisationInformation.class);

        List<Resource> resources = AppointmentTransformer.transformToAppointmentResources(patientId, patientAppointmentList, organisationInformation);
        return BundleHelper.createBundle(bundleProperties, resources);
    }
}
