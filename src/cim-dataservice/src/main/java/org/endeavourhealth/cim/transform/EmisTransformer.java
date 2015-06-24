package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.openhr.OpenHRTransformer;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

public class EmisTransformer implements Transformer {
	private OpenHRTransformer _openHrTransformer = new OpenHRTransformer();

	public Bundle toFHIRBundle(String sourceData) throws TransformException {
		return _openHrTransformer.toFHIRBundle(sourceData);
	}

	public Patient toFHIRPatient(String sourceData) throws TransformException {
		return _openHrTransformer.toFHIRPatient(sourceData);
	}

	public String fromFHIRCondition(Condition condition) throws TransformException {
		return _openHrTransformer.fromFHIRCondition(condition);
	}
}
