package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DiagnosticOrder;
import org.hl7.fhir.instance.model.Meta;

public class DiagnosticOrderTransformer implements ClinicalResourceTransformer
{
    public DiagnosticOrder transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        DiagnosticOrder target = new DiagnosticOrder();
        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_DIAGNOSTIC_ORDER));

        return target;
    }
}