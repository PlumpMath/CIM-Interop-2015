package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.List;
import java.util.Map;

class ConditionTransformer implements ClinicalResourceTransformer {
    public Condition transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        OpenHR001Problem problem = getProblem(healthDomain.getProblem(), convertId(source.getId()));

        Condition target = new Condition();
        target.setId(convertId(source.getId()));
        target.setPatient(convertPatient(source.getPatient()));
        target.setEncounter(getEncounter(container.getEventEncounterMap(), source.getId()));
        target.setAsserter(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setDateAssertedElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        target.setCategory(convertCategory());
        target.setSeverity(convertSeverity(problem.getSignificance()));

        handleAssociatedText(source, target);

        return target;
    }

    private OpenHR001Problem getProblem(List<OpenHR001Problem> problemList, String eventId) throws SourceDocumentInvalidException {
        if (problemList == null)
            return null;

        OpenHR001Problem problem = problemList.stream()
                .filter(u -> u.getId().equals(eventId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the problem is there multiple times, then it will just throw a general exception.

        return problem;
    }

    private static String convertId(String sourceId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourceId))
            throw new SourceDocumentInvalidException("Invalid Event Id");
        return sourceId;
    }

    private static Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private static Reference getEncounter(Map<String, String> eventEncouterMap, String eventId) {
        String encounterId = eventEncouterMap.get(eventId);
        if (StringUtils.isBlank(encounterId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounterId);
    }

    private static Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private static DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return ToFHIRHelper.convertPartialDateTimeToDateType(source);
    }

    private static CodeableConcept convertCategory() throws TransformFeatureNotSupportedException {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://hl7.org/fhir/condition-status")
                                .setCode("confirmed")
                                .setDisplay("Confirmed")
                );
    }

    private static CodeableConcept convertSeverity(VocProblemSignificance sourceSignificance) throws TransformFeatureNotSupportedException {
        Coding coding = new Coding();
        coding.setSystem("http://hl7.org/fhir/vs/condition-severity");

        switch (sourceSignificance) {
            case M:
                coding.setCode("255604002");
                coding.setDisplay("Mild");
                break;
            case S:
                coding.setCode("24484000");
                coding.setDisplay("Severe");
                break;
            default:
                throw new TransformFeatureNotSupportedException("VocProblemSignificance not supported: " + sourceSignificance);
        }

        return new CodeableConcept().addCoding(coding);
    }

    private static void handleAssociatedText(OpenHR001Event source, Condition target) throws SourceDocumentInvalidException {

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
                target.setNotes(value);
            } else {
                //TODO: Add Extension
            }
        }
    }
}