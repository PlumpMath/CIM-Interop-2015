package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Flag;
import org.hl7.fhir.instance.model.ProcedureRequest;

public class ProcedureRequestTransformer implements ClinicalResourceTransformer
{
    public ProcedureRequest transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        ProcedureRequest target = new ProcedureRequest();
        target.setId(source.getId());

        return target;
    }
}