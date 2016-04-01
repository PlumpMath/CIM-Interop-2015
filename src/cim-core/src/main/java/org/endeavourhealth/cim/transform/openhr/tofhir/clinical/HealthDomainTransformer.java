package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Component;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

public class HealthDomainTransformer {
    public static void transform(FhirContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001HealthDomain healthDomain = openHR.getHealthDomain();
        if (healthDomain == null)
            return;

        buildEventEncounterMap(container, healthDomain);

        EventTransformer.transform(container, healthDomain);

        // encounter transform must come after event transform as there is a dependency on a populated container
        EncounterTransformer.transform(container, healthDomain);
    }

    private static void buildEventEncounterMap(FhirContainer container, OpenHR001HealthDomain healthDomain) throws TransformFeatureNotSupportedException {
        for (OpenHR001Encounter encounter : healthDomain.getEncounter()) {
            for (OpenHR001Component component : encounter.getComponent()) {
                OpenHRHelper.ensureDboNotDelete(component);
                container.addEventEncounterMap(component.getEvent(), encounter);
            }
        }
    }
}
