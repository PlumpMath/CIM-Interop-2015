package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.*;

public class AllergyTransformer implements ClinicalResourceTransformer
{
    public AllergyIntolerance transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        AllergyIntolerance target = new AllergyIntolerance();
        target.setId(source.getId());
        target.setStatus(AllergyIntolerance.AllergyIntoleranceStatus.ACTIVE);
        target.setOnsetElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setRecorder(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));
        target.setSubstance(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        return target;
    }

    private DateTimeType convertEffectiveDateTime(DtDatePart source) throws TransformException
    {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return OpenHRHelper.convertPartialDateTimeToDateTimeType(source);
    }
}
