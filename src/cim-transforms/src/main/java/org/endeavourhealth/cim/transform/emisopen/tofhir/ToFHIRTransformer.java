package org.endeavourhealth.cim.transform.emisopen.tofhir;

import org.endeavourhealth.cim.common.BundleHelper;
import org.endeavourhealth.cim.common.text.TextUtils;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.AppointmentTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.ScheduleTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.SlotTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomorganisationinformation.OrganisationInformation;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;

public class ToFHIRTransformer {

    public static Bundle transformToAppointmentBundle(String baseURI, String patientGuid, PatientAppointmentList patientAppointmentList, OrganisationInformation organisationInformation) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = AppointmentTransformer.transformToAppointmentResources(patientGuid, patientAppointmentList, organisationInformation);

        return BundleHelper.createBundle(Bundle.BundleType.COLLECTION, null, baseURI, resources);
    }

    public static Bundle transformToScheduleBundle(String baseURI, AppointmentSessionList appointmentSessionList, OrganisationInformation organisationInformation) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = ScheduleTransformer.transformToScheduleResources(appointmentSessionList, organisationInformation);

        return BundleHelper.createBundle(Bundle.BundleType.COLLECTION, null, baseURI, resources);
    }

    public static Bundle transformToSlotBundle(String baseURI, String scheduleId, SlotListStruct appointmentSlotList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = SlotTransformer.transformToSlotResources(scheduleId, appointmentSlotList);

        return BundleHelper.createBundle(Bundle.BundleType.COLLECTION, null, baseURI, resources);
    }
}
