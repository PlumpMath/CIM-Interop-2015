package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.*;

class ImmunisationTransformer implements ClinicalResourceTransformer {
    public Immunization transform(OpenHR001HealthDomain healthDomain, FhirContainer container, OpenHR001HealthDomain.Event source) throws TransformException
    {
        Immunization target = new Immunization();
        target.setId(source.getId());
        target.setStatus(MedicationAdministration.MedicationAdministrationStatus.COMPLETED.toCode());
        target.setDateElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setPatient(convertPatient(source.getPatient()));
        target.setPerformer(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setEncounter(getEncounter(container, source.getId()));
        target.setVaccineCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        return target;
    }

    private DateTimeType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return OpenHRHelper.convertPartialDateTimeToDateTimeType(source);
    }

    private Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private Reference getEncounter(FhirContainer container, String eventId) {
        OpenHR001Encounter encounter = container.getEncounterFromEventId(eventId);
        if (encounter == null)
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounter.getId());
    }

}