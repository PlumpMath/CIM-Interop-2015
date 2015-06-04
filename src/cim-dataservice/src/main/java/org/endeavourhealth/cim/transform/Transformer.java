package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

public interface Transformer {
    Bundle toFHIRBundle(String sourceData) throws TransformException;
    Patient toFHIRPatient(String sourceData) throws TransformException;

    String fromFHIRCondition(Condition condition) throws TransformException;
}
