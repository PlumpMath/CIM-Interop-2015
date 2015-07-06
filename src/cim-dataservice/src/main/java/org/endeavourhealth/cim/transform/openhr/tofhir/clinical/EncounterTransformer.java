package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.math.BigDecimal;
import java.util.List;

public class EncounterTransformer {
    public static void transform(FHIRContainer container, OpenHR001HealthDomain healthDomain) throws TransformException {
        for (OpenHR001Encounter source: healthDomain.getEncounter()) {
            addOrganisationToResults(container, createEncounter(healthDomain, source));
            addEventEncouterMapping(container, source);
        }
    }

    private static Encounter createEncounter(OpenHR001HealthDomain healthDomain, OpenHR001Encounter source) throws TransformException {
        ToFHIRHelper.ensureDboNotDelete(source);

        Encounter target = new Encounter();
        target.setId(convertId(source.getId()));
        target.setStatus(convertStatus(source.isComplete()));
        target.setClass_(Encounter.EncounterClass.AMBULATORY);
        target.addType(convertType());
        target.setPatient(convertPatient(source.getPatient()));
        target.addParticipant(convertEncounterParticipant(source.getAuthorisingUserInRole()));
        target.setPeriod(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setServiceProvider(convertOrganisation(source.getOrganisation()));
        target.addLocation(convertLocation(source.getLocation()));
        target.setLength(convertDuration(source.getDuration()));

        //TODO: accompanyingHCP
        //TODO: locationType
        //TODO: clinicalPurpose

        return target;
    }

    private static void addOrganisationToResults(FHIRContainer container, Encounter encounter) throws SourceDocumentInvalidException {
        container.getEncounters().put(encounter.getId(), encounter);
    }

    private static String convertId(String sourceId) throws SourceDocumentInvalidException {
        // TODO: consider checking if Id is a valid guid
        if (StringUtils.isBlank(sourceId))
            throw new SourceDocumentInvalidException("Invalid Encounter Id");
        return sourceId;
    }

    private static Encounter.EncounterState convertStatus(boolean isComplete) {
        if (isComplete)
            return Encounter.EncounterState.FINISHED;
        else
            return Encounter.EncounterState.INPROGRESS;
    }

    private static CodeableConcept convertType() {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://snomed.info/sct")
                                .setCode("11429006")
                                .setDisplay("Consultation")
                );
    }

    private static Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        // TODO: consider checking id Id is a valid guid
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return new Reference().setReference(ReferenceHelper.createResourceReference(Patient.class, sourcePatientId));
    }

    private static Period convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return new Period().setEndElement(ToFHIRHelper.convertPartialDateTimeToDateTimeType(source));
    }

    private static Reference convertOrganisation(List<String> sourceOrganisations) throws SourceDocumentInvalidException {
        if (sourceOrganisations == null)
            return null;

        String organisationId = sourceOrganisations.stream()
                .collect(StreamExtension.singleOrNullCollector());

        //if multiple organisations exists, then it will just throw a general exception.

        if (StringUtils.isBlank(organisationId))
            throw new SourceDocumentInvalidException("Organisation not found");

        return new Reference().setReference(ReferenceHelper.createResourceReference(Organization.class, organisationId));
    }

    private static Duration convertDuration(DtDuration sourceDuration) {
        if (sourceDuration == null)
            return null;

        Duration target = new Duration();
        target.setValue(new BigDecimal(sourceDuration.getValue()));
        target.setUnits("minutes");
        target.setSystem("http://snomed.info/sct");
        target.setCode("258701004");
        return target;
    }

    private static Encounter.EncounterParticipantComponent convertEncounterParticipant(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return new Encounter.EncounterParticipantComponent()
                    .setIndividual(new Reference()
                            .setReference(ReferenceHelper.createResourceReference(Practitioner.class, userInRoleId)));
    }

    private static Encounter.EncounterLocationComponent convertLocation(String locationId) {
        if (StringUtils.isBlank(locationId))
            return null;

        // TODO: consider checking id Id is a valid guid

        return new Encounter.EncounterLocationComponent()
                .setLocation(new Reference()
                        .setReference(ReferenceHelper.createResourceReference(Location.class, locationId)));
    }

    private static void addEventEncouterMapping(FHIRContainer container, OpenHR001Encounter source) throws TransformException {
        if (source.getComponent() == null)
            return;

        String encouterId = convertId(source.getId());

        for (OpenHR001Component component: source.getComponent()) {
            ToFHIRHelper.ensureDboNotDelete(component);

            container.getEventEncouterMap().putIfAbsent(convertId(component.getEvent()), encouterId);
        }
    }
}