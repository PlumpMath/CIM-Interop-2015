package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.FHIRConstants;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EncounterTransformer {
    private final static String EXT_ENCOUNTER_COMPOSITION = "urn:fhir.nhs.uk:extension/EncounterComposition";
    private final static String EXT_ENCOUNTER_COMPOSITION_TOPIC = "Topic";
    private final static String EXT_ENCOUNTER_COMPOSITION_CATEGORY = "Category";
    private final static String EXT_ENCOUNTER_COMPOSITION_COMPONENT = "Component";

    private final static String PROBLEM_HEADING_TERM = "Problem";

    //TODO: This concept has been deprecated. There is no alternative to this code
    private final static String DEFAULT_TOPIC_DISPLAY = "Unspecified conditions";
    private final static String DEFAULT_TOPIC_CODE = "315645005";

    static class EncounterSection {
        private DtCode heading;
        private List<String> events;

        public DtCode getHeading() {
            return heading;
        }

        public void setHeading(DtCode heading) {
            this.heading = heading;
        }

        public List<String> getEvents() {
            if (events == null) events = new ArrayList<>();
            return events;
        }

        public void setEvents(List<String> events) {
            this.events = events;
        }
    }

    static class EncounterPage {
        private short pageNumber;
        private List<EncounterSection> sections;

        public short getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(short pageNumber) {
            this.pageNumber = pageNumber;
        }

        public List<EncounterSection> getSections() {
            if (sections == null) sections = new ArrayList<>();
            return sections;
        }

        public void setSections(List<EncounterSection> sections) {
            this.sections = sections;
        }
    }

    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001Encounter source: healthDomain.getEncounter()) {
            addEncounterToResults(container, createEncounter(healthDomain, container, source));
            addEventEncounterMapping(container, source);
        }
    }

    private static Encounter createEncounter(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001Encounter source) throws TransformException {
        ToFHIRHelper.ensureDboNotDelete(source);

        Encounter target = new Encounter();
        target.setId(source.getId());
        target.setStatus(convertStatus(source.isComplete()));
        target.setClass_(Encounter.EncounterClass.AMBULATORY);
        target.addType(convertType());
        target.setPatient(createPatientReference(source.getPatient()));
        target.addParticipant(createParticipantFromAuthorisingUser(source.getAuthorisingUserInRole()));
        target.setPeriod(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setServiceProvider(createOrganisationReference(source.getOrganisation()));
        target.addLocation(convertLocation(source.getLocation()));
        target.setLength(convertDuration(source.getDuration()));

        addAccompanyingHCPsAsAttenderParticipants(source.getAccompanyingHCP(), target);

        //TODO: locationType
        //TODO: clinicalPurpose

        addComponents(source, target, container);

        return target;
    }

    private static void addEncounterToResults(FHIRContainer container, Encounter encounter) throws SourceDocumentInvalidException {
        container.getClinicalResources().put(encounter.getId(), encounter);
    }

    private static Encounter.EncounterState convertStatus(boolean isComplete) {
        return (isComplete)
                ? Encounter.EncounterState.FINISHED
                : Encounter.EncounterState.INPROGRESS;
    }

    private static CodeableConcept convertType() {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem(FHIRConstants.CODE_SYSTEM_SNOMED_CT)
                                .setCode("11429006")
                                .setDisplay("Consultation")
                );
    }

    private static Reference createPatientReference(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private static Period convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return new Period().setEndElement(ToFHIRHelper.convertPartialDateTimeToDateTimeType(source));
    }

    private static Reference createOrganisationReference(List<String> sourceOrganisations) throws SourceDocumentInvalidException {
        if (sourceOrganisations == null)
            return null;

        String organisationId = sourceOrganisations.stream()
                .collect(StreamExtension.singleOrNullCollector());

        //if multiple organisations exists, then it will just throw a general exception.

        if (StringUtils.isBlank(organisationId))
            throw new SourceDocumentInvalidException("Organisation not found");

        return ReferenceHelper.createReference(ResourceType.Organization, organisationId);
    }

    private static Duration convertDuration(DtDuration sourceDuration) {
        if (sourceDuration == null)
            return null;

        Duration target = new Duration();
        target.setSystem(FHIRConstants.CODE_SYSTEM_SNOMED_CT);
        target.setUnits("minutes");
        target.setCode("258701004");
        target.setValue(new BigDecimal(sourceDuration.getValue()));
        return target;
    }

    private static Encounter.EncounterParticipantComponent createParticipantFromAuthorisingUser(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return new Encounter.EncounterParticipantComponent()
        .addType(new CodeableConcept()
                .addCoding(new Coding()
                        .setSystem("http://hl7.org/fhir/v3/ParticipationType")
                        .setDisplay("consultant")
                        .setCode("CON")))
                .setIndividual(ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId));
    }

    private static Encounter.EncounterLocationComponent convertLocation(String locationId) {
        if (StringUtils.isBlank(locationId))
            return null;

        return new Encounter.EncounterLocationComponent()
            .setLocation(ReferenceHelper.createReference(ResourceType.Location, locationId));
    }

    private static void addAccompanyingHCPsAsAttenderParticipants(List<String> userInRoleIds, Encounter target) throws SourceDocumentInvalidException {
        if (userInRoleIds == null)
            return;

        for (String userInRoleId: userInRoleIds) {
            target.addParticipant(new Encounter.EncounterParticipantComponent()
                    .addType(new CodeableConcept()
                            .addCoding(new Coding()
                                    .setSystem("http://hl7.org/fhir/v3/ParticipationType")
                                    .setDisplay("attender")
                                    .setCode("ATND")))
                    .setIndividual(ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId)));
        }
    }

    private static void addEventEncounterMapping(FHIRContainer container, OpenHR001Encounter source) throws TransformException {
        if (source.getComponent() == null)
            return;

        for (OpenHR001Component component: source.getComponent()) {
            ToFHIRHelper.ensureDboNotDelete(component);

            container.getEventEncounterMap().putIfAbsent(component.getEvent(), source.getId());
        }
    }

    private static void addComponents(OpenHR001Encounter source, Encounter target, FHIRContainer container) throws SourceDocumentInvalidException {
        if (source.getComponent() == null || source.getComponent().isEmpty())
            return;

        List<EncounterPage> pages = convertFlatComponentListToPageAndSectionHierarchy(source.getComponent());

        Extension compositionExtension = new Extension()
                .setUrl(EXT_ENCOUNTER_COMPOSITION);

        for (EncounterPage page: pages) {
            CodeableConcept topicExtensionValue = getTopicCodeFromEncounterPage(page, container);

            for (EncounterSection section: page.getSections()) {
                CodeableConcept categoryExtensionValue =  new CodeableConcept()
                        .addCoding(new Coding()
                                .setSystem(FHIRConstants.CODE_SYSTEM_SNOMED_CT)
                                .setDisplay(section.getHeading().getDisplayName())
                                .setCode(section.getHeading().getCode()));

                for (String eventId: section.getEvents()) {
                    categoryExtensionValue.addExtension(new Extension()
                            .setUrl(EXT_ENCOUNTER_COMPOSITION_COMPONENT)
                            .setValue(createResourceReferenceFromEvent(container, eventId)));
                }

                topicExtensionValue.addExtension(new Extension()
                        .setUrl(EXT_ENCOUNTER_COMPOSITION_CATEGORY)
                        .setValue(categoryExtensionValue));
            }

            compositionExtension.addExtension(new Extension()
                    .setUrl(EXT_ENCOUNTER_COMPOSITION_TOPIC)
                    .setValue(topicExtensionValue));
        }

        target.addExtension(compositionExtension);
    }

    private static Reference createResourceReferenceFromEvent(FHIRContainer container, String eventId) throws SourceDocumentInvalidException {
        // find event resource in container
        Resource resource = container.getClinicalResources().get(eventId);
        if (resource == null)
            throw new SourceDocumentInvalidException("Encounter component event resource not found in container. EventId:" + eventId);

        return ReferenceHelper.createReference(resource.getResourceType(), eventId);
    }

    private static List<EncounterPage> convertFlatComponentListToPageAndSectionHierarchy(List<OpenHR001Component> components) {
        List<EncounterPage> pages = new ArrayList<>();

        EncounterPage currentPage = null;
        EncounterSection currentSection = null;
        for (OpenHR001Component component: components) {
            if (currentPage == null || currentPage.getPageNumber() != component.getProblemPage()) {
                currentPage = new EncounterPage();
                currentPage.setPageNumber(component.getProblemPage());
                pages.add(currentPage);
                currentSection = null;
            }

            if (currentSection == null || !currentSection.getHeading().getDisplayName().equals(component.getHeading().getDisplayName())) {
                currentSection = new EncounterSection();
                currentSection.setHeading(component.getHeading());
                currentPage.getSections().add(currentSection);
            }

            currentSection.getEvents().add(component.getEvent());
        }

        return pages;
    }

    private static CodeableConcept getTopicCodeFromEncounterPage(EncounterPage page, FHIRContainer container) {
        CodeableConcept topicCode = null;
        for (EncounterSection section: page.getSections()) {
            if (section.getHeading().getDisplayName().equals(PROBLEM_HEADING_TERM)) {
                // Problem sections should only have a single event
                String eventId = section.events.stream()
                        .collect(StreamExtension.singleOrNullCollector());
                if (StringUtils.isNotBlank(eventId)) {
                    topicCode = getCodeFromResource(container.getClinicalResources().get(eventId));
                }

                break;
            }
        }

        // if topic (problem) code not found add default
        if (topicCode == null) {
            topicCode = new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem(FHIRConstants.CODE_SYSTEM_SNOMED_CT)
                            .setDisplay(DEFAULT_TOPIC_DISPLAY)
                            .setCode(DEFAULT_TOPIC_CODE));
        }

        return topicCode;
    }

    private static CodeableConcept getCodeFromResource(Resource resource) {
        if (resource == null)
            return null;

        CodeableConcept result = null;
        if (resource.getResourceType() == ResourceType.Condition) {
            Condition condition = (Condition)resource;
            if (condition.hasCode())
                result = condition.getCode().copy();
        } else if (resource.getResourceType() == ResourceType.Observation) {
            Observation observation = (Observation)resource;
            if (observation.hasCode())
                result = observation.getCode().copy();
        }
        return result;
    }

}