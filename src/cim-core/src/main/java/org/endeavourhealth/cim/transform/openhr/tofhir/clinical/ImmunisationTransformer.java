package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.*;

public class ImmunisationTransformer implements ClinicalResourceTransformer
{
    public Immunization transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        Immunization target = new Immunization();
        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_IMMUNIZATION));

        target.setStatus(MedicationAdministration.MedicationAdministrationStatus.COMPLETED.toCode());
        target.setDateElement(OpenHRHelper.convertPartialDateTimeToDateTimeType(source.getEffectiveTime()));
        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setPerformer(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));
        target.setEncounter(eventEncounterMap.getEncounterReference(source.getId()));
        target.setVaccineCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));
        return target;
    }
}