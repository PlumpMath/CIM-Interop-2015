package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.CodeHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.hl7.fhir.instance.model.*;

import java.util.Date;

public class MedicationTransformer implements ClinicalResourceTransformer
{
    public MedicationOrder transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        MedicationOrder target = new MedicationOrder();

        target.setId(source.getId());
        target.setDateWritten(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setPrescriber(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));
        target.setEncounter(eventEncounterMap.getEncounterReference(source.getId()));
        target.setMedication(createMedication(source, target));

        switch (source.getEventType())
        {
            case ISS: // Medication Issue
                convertMedicationIssue(source.getMedicationIssue(), target);
                break;
            case MED: // Medication
                convertMedication(source.getMedication(), target);
                break;
            default:
                throw new TransformFeatureNotSupportedException("Invalid Medication Event Type: " + source.getEventType().toString());
        }

        return target;
    }

    private static MedicationOrder.MedicationOrderStatus getStatus(VocDrugStatus drugStatus) {

        switch (drugStatus)
        {
            case A: return MedicationOrder.MedicationOrderStatus.ACTIVE;
            case N: return MedicationOrder.MedicationOrderStatus.DRAFT;
            case C:
            default: return MedicationOrder.MedicationOrderStatus.COMPLETED;
        }
    }

    private Date convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return TransformHelper.toDate(source.getValue());
    }

    private Reference createMedication(OpenHR001HealthDomain.Event source, MedicationOrder target) throws TransformFeatureNotSupportedException {
        Medication medication = new Medication();
        medication.setId("medication");
        medication.setCode(CodeHelper.convertCode(source.getCode(), source.getDisplayTerm()));

        target.getContained().add(medication);
        return ReferenceHelper.createInternalReference(medication.getId());
    }

    private void convertMedicationIssue(OpenHR001MedicationIssue medicationIssue, MedicationOrder target) {
        target.addDosageInstruction(new MedicationOrder.MedicationOrderDosageInstructionComponent()
                .setText(medicationIssue.getDosage())
                .setDose(new SimpleQuantity()
                        .setValue(medicationIssue.getQuantity())
                        .setUnit(medicationIssue.getQuantityUnit())))
                .setStatus(MedicationOrder.MedicationOrderStatus.COMPLETED);
    }

    private void convertMedication(OpenHR001Medication medication, MedicationOrder target) {
        target.addDosageInstruction(new MedicationOrder.MedicationOrderDosageInstructionComponent()
                .setText(medication.getDosage())
                .setDose(new SimpleQuantity()
                        .setValue(medication.getQuantity())
                        .setUnit(medication.getQuantityUnit())))
                .setStatus(getStatus(medication.getDrugStatus()));

    }

}
