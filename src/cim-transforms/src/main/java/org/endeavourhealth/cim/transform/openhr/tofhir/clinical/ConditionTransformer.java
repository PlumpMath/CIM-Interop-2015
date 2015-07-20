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
import java.util.stream.Collectors;

class ConditionTransformer implements ClinicalResourceTransformer {
    private final static String CONDITION_LINK_EXTENSION = "urn:fhir.nhs.uk:extension/ConditionLink";
    private final static String CONDITION_LINK_TYPE_SYSTEM = "urn:fhir.nhs.uk:vs/ConditionLinkType";
    private final static String CONDITION_LINK_TYPE_EPISODE_CODE = "is-episode";
    private final static String CONDITION_LINK_TYPE_EPISODE_DISPLAY = "Episode follow on";
    private final static String CONDITION_LINK_TYPE_ASSOCIATION_CODE = "has-association";
    private final static String CONDITION_LINK_TYPE_ASSOCIATION_DISPLAY = "Has association";

    public Condition transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        OpenHR001Problem problem = getProblem(healthDomain.getProblem(), convertId(source.getId()));

        Condition target = new Condition();
        target.setId(convertId(source.getId()));
        target.setPatient(convertPatient(source.getPatient()));
        target.setEncounter(getEncounter(container, source.getId()));
        target.setAsserter(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setDateAssertedElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        target.setCategory(convertCategory());
        target.setSeverity(convertSeverity(problem.getSignificance()));

        convertAssociatedText(source, target);

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

    private String convertId(String sourceId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourceId))
            throw new SourceDocumentInvalidException("Invalid Event Id");
        return sourceId;
    }

    private Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private Reference getEncounter(FHIRContainer container, String eventId) {
        OpenHR001Encounter encounter = container.getEncounterFromEventId(eventId);
        if (encounter == null)
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounter.getId());
    }

    private Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return ToFHIRHelper.convertPartialDateTimeToDateType(source);
    }

    private CodeableConcept convertCategory() throws TransformFeatureNotSupportedException {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://hl7.org/fhir/condition-status")
                                .setCode("confirmed")
                                .setDisplay("Confirmed")
                );
    }

    private CodeableConcept convertSeverity(VocProblemSignificance sourceSignificance) throws TransformFeatureNotSupportedException {
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

    private void convertAssociatedText(OpenHR001Event source, Condition target) throws SourceDocumentInvalidException {

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

    public static void buildConditionLinks(List<Condition> conditions, List<OpenHR001Problem> problems, FHIRContainer container) throws TransformException {
        for (Condition targetCondition: conditions) {
            OpenHR001Problem sourceProblems = problems
                    .stream()
                    .filter(p -> p.getId().equals(targetCondition.getId()))
                    .collect(StreamExtension.singleCollector());
            addEventLinks(targetCondition, sourceProblems, container);
        }
    }

    private static void addEventLinks(Condition target, OpenHR001Problem problem, FHIRContainer container) throws SourceDocumentInvalidException {
        if (problem.getEventLink().isEmpty())
            return;

        List_ conditionLinkList = createConditionLinkList(problem.getEventLink(), container);
        target.getContained().add(conditionLinkList);

        target.addExtension(new Extension()
            .setUrl(CONDITION_LINK_EXTENSION)
            .setValue(ReferenceHelper.createInternalReference(conditionLinkList.getId())));
    }

    private static List_ createConditionLinkList(List<OpenHR001ProblemEventLink> eventLinks, FHIRContainer container) throws SourceDocumentInvalidException {
        List_ conditionLinkList = new List_();
        conditionLinkList.setId("condition-links");
        conditionLinkList.setStatus(List_.ListStatus.CURRENT);
        conditionLinkList.setMode(List_.ListMode.WORKING);

        for (OpenHR001ProblemEventLink eventLink: eventLinks) {
            conditionLinkList.addEntry(new List_.ListEntryComponent()
                    .addFlag(new CodeableConcept()
                            .addCoding(eventLink.getEventLinkType() == VocProblemEventLinkType.FOL
                                    ? new Coding()
                                        .setSystem(CONDITION_LINK_TYPE_SYSTEM)
                                        .setCode(CONDITION_LINK_TYPE_EPISODE_CODE)
                                        .setDisplay(CONDITION_LINK_TYPE_EPISODE_DISPLAY)
                                    : new Coding()
                                        .setSystem(CONDITION_LINK_TYPE_SYSTEM)
                                        .setCode(CONDITION_LINK_TYPE_ASSOCIATION_CODE)
                                        .setDisplay(CONDITION_LINK_TYPE_ASSOCIATION_DISPLAY)))
                    .setItem(createResourceReferenceFromEvent(container, eventLink.getId())));
        }

        return conditionLinkList;
    }

    private static Reference createResourceReferenceFromEvent(FHIRContainer container, String eventId) throws SourceDocumentInvalidException {
        // find event resource in container
        Resource resource = container.getResourceById(eventId);
        if (resource == null)
            throw new SourceDocumentInvalidException("Condition Link Event not found in container. EventId:" + eventId);

        return ReferenceHelper.createReference(resource.getResourceType(), resource.getId());
    }

}