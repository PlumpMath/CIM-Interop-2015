package org.endeavourhealth.transform.openhr.tofhir.clinical;

import org.endeavourhealth.transform.exceptions.TransformException;
import org.endeavourhealth.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Flag;

class AlertTransformer implements ClinicalResourceTransformer {
    public Flag transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException
    {
        Flag target = new Flag();
        target.setId(source.getId());

        return target;
    }
}