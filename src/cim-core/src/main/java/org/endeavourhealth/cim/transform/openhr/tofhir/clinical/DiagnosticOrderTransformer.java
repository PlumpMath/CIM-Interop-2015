package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticOrder;

class DiagnosticOrderTransformer implements ClinicalResourceTransformer {
    public DiagnosticOrder transform(OpenHR001HealthDomain healthDomain, FhirContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        DiagnosticOrder target = new DiagnosticOrder();
        target.setId(source.getId());

        return target;
    }
}