package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Schedule;

public interface Transformer {
    Bundle toFHIRBundle(String sourceData) throws TransformException;
    Patient toFHIRPatient(String sourceData) throws TransformException;
    Bundle toFHIRAppointmentBundle(String sourceData) throws TransformException;
    Bundle toFHIRScheduleBundle(String sourceData) throws TransformException;
    Bundle toFHIRSlotBundle(String sourceData, String scheduleId) throws TransformException;

    String fromFHIRCondition(Condition condition) throws TransformException;
}
