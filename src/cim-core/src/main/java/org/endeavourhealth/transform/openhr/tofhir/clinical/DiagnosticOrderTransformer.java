package org.endeavourhealth.transform.openhr.tofhir.clinical;

import org.endeavourhealth.transform.exceptions.TransformException;
import org.endeavourhealth.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticOrder;

class DiagnosticOrderTransformer implements ClinicalResourceTransformer {
    public DiagnosticOrder transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        DiagnosticOrder target = new DiagnosticOrder();
        target.setId(source.getId());

        return target;
    }
}