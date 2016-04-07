package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.CodeHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

public class MedicationStatementTransformer implements ClinicalResourceTransformer
{
    public MedicationStatement transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        MedicationStatement target = new MedicationStatement();

        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_MEDICATION_AUTHORISATION));

        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));

        if (source.getEnteredByUserInRole() != null)
            target.setInformationSource(ReferenceHelper.createReference(ResourceType.Practitioner, source.getEnteredByUserInRole()));

        target.setStatus(getStatus(source.getMedication().getDrugStatus()));

        target.setMedication(CodeHelper.convertCode(source.getCode()));

        return target;
    }

    private static MedicationStatement.MedicationStatementStatus getStatus(VocDrugStatus drugStatus)
    {
        switch (drugStatus)
        {
            case A: return MedicationStatement.MedicationStatementStatus.ACTIVE;
            case N: return MedicationStatement.MedicationStatementStatus.ENTEREDINERROR;
            case C:
            default: return MedicationStatement.MedicationStatementStatus.COMPLETED;
        }
    }
}
