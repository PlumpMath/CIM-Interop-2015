package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

public interface Transformer {

    /* administrative */
    Bundle eopenToFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException;
    Bundle eopenToFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml) throws TransformException;
    Bundle eopenToFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException;

    /* clinical */
    Bundle openHRToFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException;
    String fromFHIRCondition(Condition condition) throws TransformException;

    /* demographic */
    Patient toFhirPatient(String openHRXml) throws TransformException;
}
