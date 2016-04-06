package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.FamilyMemberHistory;

public class FamilyHistoryTransformer implements ClinicalResourceTransformer
{
    public FamilyMemberHistory transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        FamilyMemberHistory target = new FamilyMemberHistory();
        target.setId(source.getId());

        return target;
    }
}