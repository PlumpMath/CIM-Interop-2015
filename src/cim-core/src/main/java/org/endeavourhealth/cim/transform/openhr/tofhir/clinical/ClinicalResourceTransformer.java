package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Resource;

public interface ClinicalResourceTransformer
{
    Resource transform(OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain.Event source) throws TransformException;
}