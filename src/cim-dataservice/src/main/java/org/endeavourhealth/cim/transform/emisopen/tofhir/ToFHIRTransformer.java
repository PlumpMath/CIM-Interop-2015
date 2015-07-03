package org.endeavourhealth.cim.transform.emisopen.tofhir;

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

    public static Bundle transformToAppointmentBundle(PatientAppointmentList patientAppointmentList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = AppointmentTransformer.transformToAppointmentResources(patientAppointmentList);

        return createBundle(resources, null);
    }

    public static Bundle transformToScheduleBundle(AppointmentSessionList appointmentSessionList) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = ScheduleTransformer.transformToScheduleResources(appointmentSessionList);

        return createBundle(resources, null);
    }

    public static Bundle transformToSlotBundle(SlotListStruct appointmentSlotList, String scheduleId) throws TransformFeatureNotSupportedException, SerializationException {
        ArrayList<Resource> resources = SlotTransformer.transformToSlotResources(appointmentSlotList, scheduleId);

        return createBundle(resources, null);
    }

    private static Bundle createBundle(ArrayList<Resource> resources, String id) {
        Bundle bundle = new Bundle()
                .setType(Bundle.BundleType.COLLECTION)
                .setBase(EmisOpenCommon.EMISOPEN_NAMESPACE);

        if (!TextUtils.isNullOrTrimmedEmpty(id))
            bundle.setId(id);

        resources.forEach(t -> bundle.addEntry(new Bundle.BundleEntryComponent().setResource(t)));

        return bundle;
    }
}
