package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Encounter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.*;

class AllergyTransformer implements ClinicalResourceTransformer
{
    public AllergyIntolerance transform(OpenHR001HealthDomain healthDomain, FhirContainer container, EventEncounterMap eventEncounterMap, OpenHR001HealthDomain.Event source) throws TransformException
    {
        AllergyIntolerance target = new AllergyIntolerance();
        target.setId(source.getId());
        target.setStatus(AllergyIntolerance.AllergyIntoleranceStatus.ACTIVE);
        target.setOnsetElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setPatient(convertPatient(source.getPatient()));
        target.setRecorder(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setSubstance(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        return target;
    }

    private DateTimeType convertEffectiveDateTime(DtDatePart source) throws TransformException
    {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return OpenHRHelper.convertPartialDateTimeToDateTimeType(source);
    }

    private Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException
    {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException
    {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }
}
