package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Meta;
import org.hl7.fhir.instance.model.Procedure;
import org.hl7.fhir.instance.model.Resource;

public class ProcedureTransformer implements ClinicalResourceTransformer
{
    public Resource transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException {

        Procedure target = new Procedure();
        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_PROCEDURE));

        return target;
    }
}