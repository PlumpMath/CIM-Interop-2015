package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomuserdetails.UserDetails;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Person;
import org.hl7.fhir.instance.model.Resource;

import java.util.List;

public class EmisOpenTransformer {

    public Bundle toFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException
    {

        AppointmentSessionList appointmentSessionList = TransformHelper.unmarshall(eopenSchedulesXml, AppointmentSessionList.class);
        OrganisationInformation organisationInformation = TransformHelper.unmarshall(eopenOrganisationXml, OrganisationInformation.class);

        List<Resource> resources = ScheduleTransformer.transformToScheduleResources(appointmentSessionList, organisationInformation);
        return BundleHelper.createBundle(bundleProperties, resources);
    }

    public Bundle toFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml) throws TransformException {

        SlotListStruct slotListStruct = TransformHelper.unmarshall(eopenSlotsXml, SlotListStruct.class);

        List<Resource> resources = SlotTransformer.transformToSlotResources(scheduleId, slotListStruct);
        return BundleHelper.createBundle(bundleProperties, resources);
    }

    public Bundle toFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException {

        PatientAppointmentList patientAppointmentList = TransformHelper.unmarshall(eopenAppointmentsXml, PatientAppointmentList.class);
        OrganisationInformation organisationInformation = TransformHelper.unmarshall(organisationXml, OrganisationInformation.class);

        List<Resource> resources = AppointmentTransformer.transformToAppointmentResources(patientId, patientAppointmentList, organisationInformation);
        return BundleHelper.createBundle(bundleProperties, resources);

    }

	public Person toFhirPerson(String eopenMedicalRecordXml) throws TransformException {
		UserDetails userDetails = TransformHelper.unmarshall(eopenMedicalRecordXml, UserDetails.class);
		Person person = UserTransformer.transformToPersonResource(userDetails);
		return person;
	}
}
