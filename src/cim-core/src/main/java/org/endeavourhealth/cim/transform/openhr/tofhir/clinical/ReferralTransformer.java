package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.ReferralRequest;

class ReferralTransformer implements ClinicalResourceTransformer {
    public ReferralRequest transform(OpenHR001HealthDomain healthDomain, FhirContainer container, OpenHR001HealthDomain.Event source) throws TransformException
    {
        ReferralRequest target = new ReferralRequest();
        target.setId(source.getId());

        return target;
    }
}