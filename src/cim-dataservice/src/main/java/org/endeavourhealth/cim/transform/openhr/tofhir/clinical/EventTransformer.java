package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Resource;

public class EventTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            ToFHIRHelper.ensureDboNotDelete(source);
            ClinicalResourceTransformer transformer = ClinicalResourceTransformerFactory.getTransformerForEvent(healthDomain, source);
            addEventToResults(container, transformer.transform(healthDomain, container, source));
        }
    }

    private static void addEventToResults(FHIRContainer container, Resource resource) throws SourceDocumentInvalidException {
        if (resource != null) {
            container.getClinicalResources().put(resource.getId(), resource);
        }
    }
}