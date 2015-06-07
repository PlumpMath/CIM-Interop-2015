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

        EncounterTransformer.transform(container, healthDomain);
        EventTransformer.transform(container, healthDomain);
    }
}
