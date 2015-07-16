package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

public class HealthDomainTransformer {
    public static void transform(FHIRContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException {
        OpenHR001HealthDomain healthDomain = openHR.getHealthDomain();
        if (healthDomain == null)
            return;

        EventTransformer.transform(container, healthDomain);

        // encounter transform must come after event transform as there is a dependency on a populated container
        EncounterTransformer.transform(container, healthDomain);
    }
}
