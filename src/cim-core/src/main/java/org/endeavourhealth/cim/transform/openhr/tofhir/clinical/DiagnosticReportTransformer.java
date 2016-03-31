package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticReport;

class DiagnosticReportTransformer implements ClinicalResourceTransformer {
    public DiagnosticReport transform(OpenHR001HealthDomain healthDomain, FhirContainer container, OpenHR001HealthDomain.Event source) throws TransformException
    {
        DiagnosticReport target = new DiagnosticReport();
        target.setId(source.getId());

        return target;
    }
}