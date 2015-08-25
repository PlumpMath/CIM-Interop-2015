package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.common.BundleProperties;
import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

@SuppressWarnings("unused")
public class EmisTransformer implements Transformer {

	private final OpenHRTransformer _openHRTransformer = new OpenHRTransformer();
	private final EmisOpenTransformer _emisOpenTransformer = new EmisOpenTransformer();

	/* administrative */
	public Bundle eopenToFhirScheduleBundle(BundleProperties bundleProperties, String eopenSchedulesXml, String eopenOrganisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirScheduleBundle(bundleProperties, eopenSchedulesXml, eopenOrganisationXml);
	}

	public Bundle eopenToFhirSlotBundle(BundleProperties bundleProperties, String scheduleId, String eopenSlotsXml) throws TransformException {

		return _emisOpenTransformer.toFhirSlotBundle(bundleProperties, scheduleId, eopenSlotsXml);
	}

	public Bundle eopenToFhirAppointmentForPatientBundle(BundleProperties bundleProperties, String patientId, String eopenAppointmentsXml, String organisationXml) throws TransformException {

		return _emisOpenTransformer.toFhirAppointmentForPatientBundle(bundleProperties, patientId, eopenAppointmentsXml, organisationXml);
	}

	/* clinical */
	public Bundle openHRToFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException {

		return _openHRTransformer.toFhirBundle(bundleProperties, openHRXml);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {

		return _openHRTransformer.fromFHIRCondition(condition);
	}

	/* demographic */
	public Patient toFhirPatient(String openHRXml) throws TransformException {

		return _openHRTransformer.toFhirPatient(openHRXml);
	}

	public Bundle toFhirPersonBundle(String openHRXmlArray) throws TransformException {

		return _openHRTransformer.toFhirPersonBundle(openHRXmlArray);
	}
}
