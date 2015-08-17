package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.emisopen.EmisOpenTransformer;
import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Schedule;

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

	public Bundle toFHIRAppointmentBundle(String baseURI, String patientGuid, String appointmentsXml, String organisationXml) throws TransformException {
		return _emisOpenTransformer.toFHIRAppointmentBundle(baseURI, patientGuid, appointmentsXml, organisationXml);
	}

	public Bundle toFHIRScheduleBundle(String baseURI, String schedulesXml, String organisationXml) throws TransformException {
		return _emisOpenTransformer.toFHIRScheduleBundle(baseURI, schedulesXml, organisationXml);
	}

	public Bundle toFHIRSlotBundle(String baseURI, String scheduleId, String slotsXml, String organisationXml) throws TransformException {
		return _emisOpenTransformer.toFHIRSlotBundle(baseURI, slotsXml, scheduleId);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {
		return _openHrTransformer.fromFHIRCondition(condition);
	}
}
