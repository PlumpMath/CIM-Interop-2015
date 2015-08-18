package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.common.BundleProperties;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

@SuppressWarnings("unused")
public class EmisTransformer implements Transformer {

	private final OpenHRTransformer _openHrTransformer = new OpenHRTransformer();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();

	public Bundle toFHIRBundle(String baseURI, String openHRXml) throws TransformException {
		return _openHrTransformer.toFHIRBundle(baseURI, openHRXml);
	}

	public Patient toFHIRPatient(String sourceData) throws TransformException {
		return _openHrTransformer.toFHIRPatient(sourceData);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {
		return _openHrTransformer.fromFHIRCondition(condition);
	}

	/* administrative */

	public Bundle toFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirScheduleBundle(bundleProperties, eopenSchedulesXml, eopenOrganisationXml);
	}

	public Bundle toFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml, String eopenOrganisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirSlotBundle(bundleProperties, scheduleId, eopenSlotsXml);
	}

	public Bundle toFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirAppointmentForPatientBundle(bundleProperties, patientId, eopenAppointmentsXml, organisationXml);
	}
}
