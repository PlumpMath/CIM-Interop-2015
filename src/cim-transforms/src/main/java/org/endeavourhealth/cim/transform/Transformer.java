package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.common.BundleProperties;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

public interface Transformer {

    Bundle toFHIRBundle(String baseURI, String openHRXml) throws TransformException;
    Patient toFHIRPatient(String sourceData) throws TransformException;
    String fromFHIRCondition(Condition condition) throws TransformException;

    /* administrative */
    Bundle toFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException;
    Bundle toFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml, String eopenOrganisationXml) throws TransformException;
    Bundle toFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException;
}
