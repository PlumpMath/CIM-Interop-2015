package org.endeavourhealth.transform.openhr.tofhir.clinical;

import org.endeavourhealth.transform.exceptions.TransformException;
import org.endeavourhealth.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Condition;

import java.util.List;

class EventTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException
    {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            ToFHIRHelper.ensureDboNotDelete(source);

            ClinicalResourceTransformer transformer = ClinicalResourceTransformerFactory.getTransformerForEvent(healthDomain, source);
            container.addResource(transformer.transform(healthDomain, container, source));
        }

        postProcessEvents(container, healthDomain);
    }

    private static void postProcessEvents(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        // create condition links
        List<Condition> conditions = container.getResourcesOfType(Condition.class);
        if (!conditions.isEmpty())
            ConditionTransformer.buildConditionLinks(conditions, healthDomain.getProblem(), container);
    }
}