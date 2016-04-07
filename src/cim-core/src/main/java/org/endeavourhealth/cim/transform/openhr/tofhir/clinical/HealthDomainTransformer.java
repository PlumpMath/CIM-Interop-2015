package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class HealthDomainTransformer
{
    public static List<Resource> transform(OpenHR001HealthDomain openHRHealthDomain) throws TransformException
    {
        List<Resource> resources = transformEvents(openHRHealthDomain);

        ConditionTransformer.buildConditionLinks(resources, openHRHealthDomain.getProblem());

        resources.addAll(EncounterTransformer.transform(openHRHealthDomain, resources));

        return resources;
    }

    private static List<Resource> transformEvents(OpenHR001HealthDomain healthDomain) throws TransformException
    {
        EventEncounterMap eventEncounterMap = EventEncounterMap.Create(healthDomain);

        List<Resource> result = new ArrayList<>();

        for (OpenHR001HealthDomain.Event event : healthDomain.getEvent())
        {
            OpenHRHelper.ensureDboNotDelete(event);

            ClinicalResourceTransformer transformer = getTransformerForEvent(healthDomain, event);
            result.add(transformer.transform(event, healthDomain, eventEncounterMap));
        }

        return result;
    }

    private static ClinicalResourceTransformer getTransformerForEvent(OpenHR001HealthDomain healthDomain, OpenHR001HealthDomain.Event event) throws TransformException
    {
        if (OpenHRHelper.isProblem(healthDomain.getProblem(), event))
            return new ProblemTransformer();

        switch (event.getEventType())
        {
            case OBS:                                            // Observation
            {
                if (OpenHRHelper.isProcedure(event))
                    return new ProcedureTransformer();
                else // if condition
                    return new ConditionTransformer();
                //else
                //    return new ObservationTransformer();
            }
            case VAL:                                            // Value
            case INV:                                            // Investigation
            case ATT: return new ObservationTransformer();       // Attachment
            case DRY: return new ProcedureRequestTransformer();  // Diary
            case ISS: return new MedicationOrderTransformer();   // Medication Issue
            case MED: return new MedicationStatementTransformer();        // Medication
            case TR:  return new DiagnosticOrderTransformer();   // Test Request
            case REF: return new ReferralTransformer();          // Referral
            case ALT: return new AlertTransformer();             // Alert
            case ALL: return new AllergyTransformer();           // Allergy
            case FH:  return new FamilyHistoryTransformer();     // Family history
            case IMM: return new ImmunisationTransformer();      // Immunisation
            case REP: return new DiagnosticReportTransformer();  // Report

            default: throw new TransformFeatureNotSupportedException("Event Type not supported: " + event.getEventType().toString());
        }
    }

}
