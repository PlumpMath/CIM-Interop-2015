package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.*;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Component;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Condition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthDomainTransformer
{
    public static void transform(FhirContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001HealthDomain healthDomain = openHR.getHealthDomain();

        EventEncounterMap eventEncounterMap = EventEncounterMap.Create(healthDomain);

        transformEvents(container, eventEncounterMap, healthDomain);

        // encounter transform must come after event transform as there is a dependency on a populated container
        EncounterTransformer.transform(container, healthDomain);
    }

    private static void transformEvents(FhirContainer container, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain healthDomain) throws TransformException
    {
        for (OpenHR001HealthDomain.Event event : healthDomain.getEvent())
        {
            OpenHRHelper.ensureDboNotDelete(event);

            ClinicalResourceTransformer transformer = getTransformerForEvent(healthDomain, event);
            container.addResource(transformer.transform(healthDomain, container, eventEncounterMap, event));
        }

        List<Condition> conditions = container.getResourcesOfType(Condition.class);

        if (!conditions.isEmpty())
            ConditionTransformer.buildConditionLinks(conditions, healthDomain.getProblem(), container);
    }

    private static ClinicalResourceTransformer getTransformerForEvent(OpenHR001HealthDomain healthDomain, OpenHR001HealthDomain.Event event) throws TransformException
    {
        if (OpenHRHelper.isCondition(healthDomain.getProblem(), event))
            return new ConditionTransformer();

        switch (event.getEventType())
        {
            case OBS:                                            // Observation
            {
                if (OpenHRHelper.isProcedure(event))
                    return new ProcedureTransformer();
                else
                    return new ObservationTransformer();
            }
            case VAL: // Value
            case INV: // Investigation
            case ATT: // Attachment
            case DRY: return new ObservationTransformer();       // Diary
            case ISS:                                            // Medication Issue
            case MED: return new MedicationTransformer();        // Medication
            case TR:  return new DiagnosticOrderTransformer();   // Test Request
            case REF: return new ReferralTransformer();          // Referral
            case ALT: return new AlertTransformer();             // Alert
            case ALL: return new AllergyTransformer();           // Allergy
            case FH:  return new FamilyHistoryTransformer();     // Family history
            case IMM: return new ImmunisationTransformer();      // Immunisation
            case REP: return new DiagnosticReportTransformer();  // Report

            default:
                throw new TransformFeatureNotSupportedException("Event Type not supported: " + event.getEventType().toString());
        }
    }

}
