package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Flag;

public class AlertTransformer implements ClinicalResourceTransformer
{
    public Flag transform(OpenHR001HealthDomain healthDomain, FhirContainer container, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain.Event source) throws TransformException
    {
        Flag target = new Flag();
        target.setId(source.getId());

        return target;
    }
}