package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Schedule;

public interface Transformer {
    Bundle toFHIRBundle(String baseURI, String openHRXml) throws TransformException;
    Patient toFHIRPatient(String sourceData) throws TransformException;

    Bundle toFHIRAppointmentBundle(String baseURI, String patientGuid, String appointmentsXml, String organisationXml) throws TransformException;
    Bundle toFHIRScheduleBundle(String baseURI, String schedulesXml, String organisationXml) throws TransformException;
    Bundle toFHIRSlotBundle(String baseURI, String scheduleId, String slotsXml, String organisationXml) throws TransformException;

    String fromFHIRCondition(Condition condition) throws TransformException;
}
