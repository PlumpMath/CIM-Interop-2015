package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticReport;
import org.hl7.fhir.instance.model.Meta;

public class DiagnosticReportTransformer implements ClinicalResourceTransformer
{
    public DiagnosticReport transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        DiagnosticReport target = new DiagnosticReport();
        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_DIAGNOSTIC_REPORT));

        return target;
    }
}