package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Condition;

import java.util.List;

class EventTransformer {
    public static void transform(FhirContainer container, OpenHR001HealthDomain healthDomain) throws TransformException
    {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            OpenHRHelper.ensureDboNotDelete(source);

            ClinicalResourceTransformer transformer = ClinicalResourceTransformerFactory.getTransformerForEvent(healthDomain, source);
            container.addResource(transformer.transform(healthDomain, container, source));
        }

        postProcessEvents(container, healthDomain);
    }

    private static void postProcessEvents(FhirContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        // create condition links
        List<Condition> conditions = container.getResourcesOfType(Condition.class);
        if (!conditions.isEmpty())
            ConditionTransformer.buildConditionLinks(conditions, healthDomain.getProblem(), container);
    }
}