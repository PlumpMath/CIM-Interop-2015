package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Problem;
import org.hl7.fhir.instance.model.Resource;

import java.util.List;

public class EventTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001HealthDomain.Event source: healthDomain.getEvent()) {
            addEventToResults(container, createEvent(healthDomain, container, source));
        }
    }

    private static Resource createEvent(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        ToFHIRHelper.ensureDboNotDelete(source);

        OpenHR001Problem problem = getProblem(healthDomain.getProblem(), convertId(source.getId()));
        if (problem != null)
            return ConditionTransformer.transform(healthDomain, container, source, problem);

        switch (source.getEventType()) {
            case OBS: // Observation
            case VAL: // Value
                return ObservationTransformer.transform(healthDomain, source);
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
        // TODO: consider checking if Id is a valid guid
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

}
