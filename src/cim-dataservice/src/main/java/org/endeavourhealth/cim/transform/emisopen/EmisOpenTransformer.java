package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.emisopen.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomappointmentsessions.AppointmentSessionList;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.ObjectFactory;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomslotsforsession.SlotListStruct;
import org.hl7.fhir.instance.model.Bundle;

import java.text.SimpleDateFormat;

public class EmisOpenTransformer {
    public Bundle toFHIRScheduleBundle(String sourceData) throws TransformException {
        AppointmentSessionList appointmentSessionList = TransformHelper.unmarshall(sourceData, AppointmentSessionList.class);

        ToFHIRTransformer toFHIRTransformer = new ToFHIRTransformer();
        return toFHIRTransformer.transformToScheduleBundle(appointmentSessionList);
    }

    public Bundle toFHIRSlotBundle(String sourceData, String scheduleId) throws TransformException {
        SlotListStruct slotListStruct = TransformHelper.unmarshall(sourceData, SlotListStruct.class);

        ToFHIRTransformer toFHIRTransformer = new ToFHIRTransformer();
        return toFHIRTransformer.transformToSlotBundle(slotListStruct, scheduleId);
    }
}
