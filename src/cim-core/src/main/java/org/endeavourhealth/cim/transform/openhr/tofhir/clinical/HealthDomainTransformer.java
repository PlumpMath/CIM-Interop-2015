package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Component;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

public class HealthDomainTransformer {
    public static void transform(FHIRContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001HealthDomain healthDomain = openHR.getHealthDomain();
        if (healthDomain == null)
            return;

        buildEventEncounterMap(container, healthDomain);

        EventTransformer.transform(container, healthDomain);

        // encounter transform must come after event transform as there is a dependency on a populated container
        EncounterTransformer.transform(container, healthDomain);
    }

    private static void buildEventEncounterMap(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformFeatureNotSupportedException {
        for (OpenHR001Encounter encounter : healthDomain.getEncounter()) {
            for (OpenHR001Component component : encounter.getComponent()) {
                ToFHIRHelper.ensureDboNotDelete(component);
                container.addEventEncounterMap(component.getEvent(), encounter);
            }
        }
    }
}
