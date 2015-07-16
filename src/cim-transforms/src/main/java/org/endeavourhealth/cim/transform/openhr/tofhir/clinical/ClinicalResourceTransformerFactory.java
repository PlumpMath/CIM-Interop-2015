package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Problem;
import org.endeavourhealth.cim.transform.schemas.openhr.VocEventType;

import java.util.List;

class ClinicalResourceTransformerFactory {
    public static ClinicalResourceTransformer getTransformerForEvent(OpenHR001HealthDomain healthDomain, OpenHR001HealthDomain.Event event) throws TransformException {

        if (isCondition(healthDomain.getProblem(), event))
            return new ConditionTransformer();

        switch (event.getEventType()) {
            case OBS: // Observation
            case VAL: // Value
            case MED: // Medication
            case TR: // Test Request
            case INV: // Investigation
            case ISS: // Medication Issue
            case ATT: // Attachment
            case REF: // Referral
            case DRY: // Diary
            case ALT: // Alert
            case ALL: // Allergy
            case FH: // Family history
            case IMM: // Immunisation
            case REP: // Report
            case OH: // Order Header
                return new ObservationTransformer();
            default:
                throw new TransformFeatureNotSupportedException("Event Type not supported: " + event.getEventType().toString());
        }
    }

    private static boolean isCondition(List<OpenHR001Problem> problemList, OpenHR001HealthDomain.Event event) throws SourceDocumentInvalidException {
        // The condition resource specifically excludes AllergyIntolerance as those are handled with their own resource
        return event.getEventType() != VocEventType.ALL
                && problemList != null
                && problemList.stream().anyMatch(p -> p.getId().equals(event.getId()));
    }


/*

        private static Resource createEvent(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        ToFHIRHelper.ensureDboNotDelete(source);

        OpenHR001Problem problem = getProblem(healthDomain.getProblem(), convertId(source.getId()));
        if (problem != null)
            return ConditionTransformer.transform(healthDomain, container, source, problem);

        switch (source.getEventType()) {
            case OBS: // Observation
            case VAL: // Value
//                return ObservationTransformer.transform(healthDomain, source);
            return null;
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

    private static String convertId(String sourceId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourceId))
            throw new SourceDocumentInvalidException("Invalid Event Id");
        return sourceId;
    }

    private static OpenHR001Problem getProblem(List<OpenHR001Problem> problemList, String eventId) throws SourceDocumentInvalidException {
        if (problemList == null)
            return null;

        OpenHR001Problem problem = problemList.stream()
                .filter(u -> u.getId().equals(eventId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the problem is there multiple times, then it will just throw a general exception.

        return problem;
    }

     */

}
