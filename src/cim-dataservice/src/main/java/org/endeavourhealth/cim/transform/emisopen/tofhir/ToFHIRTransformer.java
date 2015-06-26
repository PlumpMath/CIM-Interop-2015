package org.endeavourhealth.cim.transform.emisopen.tofhir;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.ScheduleTransformer;
import org.endeavourhealth.cim.transform.emisopen.tofhir.admin.SlotTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;

public class ToFHIRTransformer
{
    public final static String EMISOPEN_NAMESPACE = "http://www.e-mis.com/emisopen/MedicalRecord";

    public static Bundle transformToScheduleBundle(AppointmentSessionList appointmentSessionList) throws TransformFeatureNotSupportedException, SerializationException
    {
        ArrayList<Resource> resources = ScheduleTransformer.transformToScheduleResources(appointmentSessionList);

        return createBundle(resources, null);
    }

    public static Bundle transformToSlotBundle(SlotListStruct appointmentSlotList)
    {
        ArrayList<Resource> resources = SlotTransformer.transformToSlotResources(appointmentSlotList);

        return createBundle(resources, null);
    }

    private static Bundle createBundle(ArrayList<Resource> resources, String id)
    {
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.COLLECTION);
        bundle.setBase(EMISOPEN_NAMESPACE);

        if ((id != null) && (id != ""))
            bundle.setId(id);

        resources.forEach(t -> bundle.addEntry(new Bundle.BundleEntryComponent().setResource(t)));

        return bundle;
    }
}
