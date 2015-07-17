package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.List;
import java.util.Map;

class ObservationTransformer implements ClinicalResourceTransformer {
    public Resource transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        Observation target = new Observation();
        target.setId(source.getId());
        target.setCode(CodeConverter.convertCode(source.getCode(), source.getDisplayTerm()));
        target.setApplies(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setIssued(TransformHelper.toDate(source.getAvailabilityTimeStamp()));
        target.setStatus(Observation.ObservationStatus.FINAL);
        target.setReliability(Observation.ObservationReliability.UNKNOWN);
        target.setSubject(convertPatient(source.getPatient()));
        target.addPerformer(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setEncounter(getEncounter(container.getEventEncounterMap(), source.getId()));

        convertAssociatedText(source, target);

        OpenHR001Observation sourceObservation = source.getObservation();

        return target;
    }

    private static DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return ToFHIRHelper.convertPartialDateTimeToDateType(source);
    }

    private static Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private static Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private static Reference getEncounter(Map<String, String> eventEncouterMap, String eventId) {
        String encounterId = eventEncouterMap.get(eventId);
        if (StringUtils.isBlank(encounterId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounterId);
    }

    private static void convertAssociatedText(OpenHR001Event source, Observation target) throws SourceDocumentInvalidException {

        List<OpenHR001Event.AssociatedText> associatedTextList = source.getAssociatedText();

        if (associatedTextList == null)
            return;

        long distinctCount = associatedTextList.stream()
                .map(a -> a.getAssociatedTextType())
                .distinct()
                .count();

        if (distinctCount != associatedTextList.size())
            throw new SourceDocumentInvalidException("AssociatedText contains duplicate types");

        for (OpenHR001Event.AssociatedText associatedText: source.getAssociatedText()) {

            VocAssociatedTextType type = associatedText.getAssociatedTextType();
            String value = StringUtils.trimToNull(associatedText.getValue());

            if (value == null)
                continue;

            if (type == VocAssociatedTextType.POST) {
                target.setComments(value);
            } else {
                //TODO: Add Extension for non post text associated text
            }
        }
    }

}

