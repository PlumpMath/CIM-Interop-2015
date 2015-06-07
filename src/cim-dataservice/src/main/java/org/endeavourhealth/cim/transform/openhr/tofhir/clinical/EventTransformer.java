package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Resource;

public class EventTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            addEventToResults(container, createEvent(healthDomain, source));
        }
    }

    private static Resource createEvent(OpenHR001HealthDomain healthDomain, OpenHR001HealthDomain.Event source) throws TransformException {
        ToFHIRHelper.ensureDboNotDelete(source);

        switch (source.getEventType()) {
            case OBS: // Observation
            case VAL: // Value
                return ObservationConverter.convert(healthDomain, source);
            case MED: // Medication
                return null;
            case TR: // Test Request
                return null;
            case INV: // Investigation
                return null;
            case ISS: // Medication Issue
                return null;
            case ATT: // Attachment
                return null;
            case REF: // Referral
                return null;
            case DRY: // Diary
                return null;
            case ALT: // Alert
                return null;
            case ALL: // Allergy
                return null;
            case FH: // Family history
                return null;
            case IMM: // Immunisation
                return null;
            case REP: // Report
                return null;
            case OH: // Order Header
                return null;
            default:
                throw new TransformFeatureNotSupportedException("Event Type not supported: " + source.getEventType().toString());
        }
    }

    private static void addEventToResults(FHIRContainer container, Resource clinicalResource) throws SourceDocumentInvalidException {
        if (clinicalResource == null)
            return;

        container.getClinicalResources().put(clinicalResource.getId(), clinicalResource);
    }
}
