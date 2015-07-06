package org.endeavourhealth.cim.transform.emisopen.tofhir;

import org.endeavourhealth.cim.common.BundleHelper;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenCommon;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.AppointmentTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.ScheduleTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.SlotTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomgetpatientappointments.PatientAppointmentList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;

public class ToFHIRTransformer {

    public static Bundle transformToAppointmentBundle(String patientGuid, PatientAppointmentList patientAppointmentList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = AppointmentTransformer.transformToAppointmentResources(patientGuid, patientAppointmentList);

        return BundleHelper.createBundle(null, EmisOpenCommon.EMISOPEN_NAMESPACE, resources);
    }

    public static Bundle transformToScheduleBundle(AppointmentSessionList appointmentSessionList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = ScheduleTransformer.transformToScheduleResources(appointmentSessionList);

        return BundleHelper.createBundle(null, EmisOpenCommon.EMISOPEN_NAMESPACE, resources);
    }

    public static Bundle transformToSlotBundle(String scheduleId, SlotListStruct appointmentSlotList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = SlotTransformer.transformToSlotResources(scheduleId, appointmentSlotList);

        return BundleHelper.createBundle(null, EmisOpenCommon.EMISOPEN_NAMESPACE, resources);
    }
}
