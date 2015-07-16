package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Schedule;

public interface Transformer {
    Bundle toFHIRBundle(String sourceData) throws TransformException;
    Patient toFHIRPatient(String sourceData) throws TransformException;

    Bundle toFHIRAppointmentBundle(String patientGuid, String appointmentsXml, String organisationXml) throws TransformException;
    Bundle toFHIRScheduleBundle(String schedulesXml, String organisationXml) throws TransformException;
    Bundle toFHIRSlotBundle(String scheduleId, String slotsXml, String organisationXml) throws TransformException;

    String fromFHIRCondition(Condition condition) throws TransformException;
}
