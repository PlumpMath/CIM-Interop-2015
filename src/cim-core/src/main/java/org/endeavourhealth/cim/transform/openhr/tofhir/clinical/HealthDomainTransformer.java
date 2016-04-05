package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Component;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

import java.util.HashMap;
import java.util.Map;

public class HealthDomainTransformer
{
    public static void transform(FhirContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001HealthDomain healthDomain = openHR.getHealthDomain();

        if (healthDomain == null)
            return;

        EventEncounterMap eventEncounterMap = EventEncounterMap.Create(healthDomain);

        EventTransformer.transform(container, eventEncounterMap, healthDomain);

        // encounter transform must come after event transform as there is a dependency on a populated container
        EncounterTransformer.transform(container, healthDomain);
    }
}
