package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.hl7.fhir.instance.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ConditionTransformer implements ClinicalResourceTransformer
{
    private final static String CONDITION_LINK_EXTENSION = "urn:fhir.nhs.uk:extension/ConditionLink";
    private final static String CONDITION_LINK_TYPE_SYSTEM = "urn:fhir.nhs.uk:vs/ConditionLinkType";
    private final static String CONDITION_LINK_TYPE_EPISODE_CODE = "is-episode";
    private final static String CONDITION_LINK_TYPE_EPISODE_DISPLAY = "Episode follow on";
    private final static String CONDITION_LINK_TYPE_ASSOCIATION_CODE = "has-association";
    private final static String CONDITION_LINK_TYPE_ASSOCIATION_DISPLAY = "Has association";

    public Condition transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        OpenHR001Problem problem = getProblem(healthDomain.getProblem(), source.getId());

        Condition target = new Condition();

        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_CONDITION));
        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setEncounter(eventEncounterMap.getEncounterReference(source.getId()));

        if (source.getAuthorisingUserInRole() != null)
            target.setAsserter(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));

        target.setDateRecordedElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        target.setCategory(convertCategory());
        target.setSeverity(convertSeverity(problem.getSignificance()));

        convertAssociatedText(source, target);

        return target;
    }

    private OpenHR001Problem getProblem(List<OpenHR001Problem> problemList, String eventId) throws TransformException {
        if (problemList == null)
            return null;

        OpenHR001Problem problem = problemList.stream()
                .filter(u -> u.getId().equals(eventId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the problem is there multiple times, then it will just throw a general exception.

        return problem;
    }

    private DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return OpenHRHelper.convertPartialDateTimeToDateType(source);
    }

    private CodeableConcept convertCategory() throws TransformFeatureNotSupportedException {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://hl7.org/fhir/condition-status")
                                .setCode("confirmed")
                                .setDisplay("Confirmed")
                );
    }

    private CodeableConcept convertSeverity(VocProblemSignificance sourceSignificance) throws TransformException {
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

    private void convertAssociatedText(OpenHR001Event source, Condition target) throws TransformException {

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

    public static void buildConditionLinks(List<Resource> resources, List<OpenHR001Problem> problems) throws TransformException
    {
        List<Condition> conditions = resources
                .stream()
                .filter(t -> t instanceof Condition)
                .map(t -> (Condition)t)
                .collect(Collectors.toList());

        for (Condition targetCondition: conditions)
        {
            OpenHR001Problem sourceProblems = problems
                    .stream()
                    .filter(p -> p.getId().equals(targetCondition.getId()))
                    .collect(StreamExtension.singleCollector());

            addEventLinks(targetCondition, sourceProblems, resources);
        }
    }

    private static void addEventLinks(Condition target, OpenHR001Problem problem, List<Resource> resources) throws TransformException
    {
        if (problem.getEventLink().isEmpty())
            return;

        List_ conditionLinkList = createConditionLinkList(problem.getEventLink(), resources);
        target.getContained().add(conditionLinkList);

        target.addExtension(new Extension()
            .setUrl(CONDITION_LINK_EXTENSION)
            .setValue(ReferenceHelper.createInternalReference(conditionLinkList.getId())));
    }

    private static List_ createConditionLinkList(List<OpenHR001ProblemEventLink> eventLinks, List<Resource> resources) throws TransformException
    {
        List_ conditionLinkList = new List_();
        conditionLinkList.setId("condition-links");
        conditionLinkList.setStatus(List_.ListStatus.CURRENT);
        conditionLinkList.setMode(List_.ListMode.WORKING);

        for (OpenHR001ProblemEventLink eventLink: eventLinks) {
            conditionLinkList.addEntry(new List_.ListEntryComponent()
                    .setFlag(new CodeableConcept()
                            .addCoding(eventLink.getEventLinkType() == VocProblemEventLinkType.FOL
                                    ? new Coding()
                                        .setSystem(CONDITION_LINK_TYPE_SYSTEM)
                                        .setCode(CONDITION_LINK_TYPE_EPISODE_CODE)
                                        .setDisplay(CONDITION_LINK_TYPE_EPISODE_DISPLAY)
                                    : new Coding()
                                        .setSystem(CONDITION_LINK_TYPE_SYSTEM)
                                        .setCode(CONDITION_LINK_TYPE_ASSOCIATION_CODE)
                                        .setDisplay(CONDITION_LINK_TYPE_ASSOCIATION_DISPLAY)))
                    .setItem(createResourceReferenceFromEvent(resources, eventLink.getId())));
        }

        return conditionLinkList;
    }

    private static Reference createResourceReferenceFromEvent(List<Resource> resources, String eventId) throws TransformException
    {
        // find event resource in container
        Resource resource = resources
                .stream()
                .filter(t -> eventId.equals(t.getId()))
                .collect(StreamExtension.singleOrNullCollector());

        if (resource == null)
            throw new SourceDocumentInvalidException("Condition Link Event not found in container. EventId:" + eventId);

        return ReferenceHelper.createReference(resource.getResourceType(), resource.getId());
    }
}