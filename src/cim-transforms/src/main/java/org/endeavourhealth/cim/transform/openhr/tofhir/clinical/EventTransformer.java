package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;

class EventTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            ToFHIRHelper.ensureDboNotDelete(source);
            ClinicalResourceTransformer transformer = ClinicalResourceTransformerFactory.getTransformerForEvent(healthDomain, source);
            container.addResource(transformer.transform(healthDomain, container, source));
        }
    }
}