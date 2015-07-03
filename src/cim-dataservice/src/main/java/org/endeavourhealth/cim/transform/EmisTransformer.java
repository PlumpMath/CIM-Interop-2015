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

	public Bundle toFHIRBundle(String sourceData) throws TransformException {
		return _openHrTransformer.toFHIRBundle(sourceData);
	}

	public Patient toFHIRPatient(String sourceData) throws TransformException {
		return _openHrTransformer.toFHIRPatient(sourceData);
	}

	public Bundle toFHIRAppointmentBundle(String sourceData) throws TransformException {
		return _emisOpenTransformer.toFHIRAppointmentBundle(sourceData);
	}

	public Bundle toFHIRScheduleBundle(String sourceData) throws TransformException {
		return _emisOpenTransformer.toFHIRScheduleBundle(sourceData);
	}

	public Bundle toFHIRSlotBundle(String sourceData, String scheduleId) throws TransformException {
		return _emisOpenTransformer.toFHIRSlotBundle(sourceData, scheduleId);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {
		return _openHrTransformer.fromFHIRCondition(condition);
	}
}
