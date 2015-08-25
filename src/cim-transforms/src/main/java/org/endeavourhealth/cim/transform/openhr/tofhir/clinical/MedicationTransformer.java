package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.Date;

class MedicationTransformer implements ClinicalResourceTransformer {
    public MedicationPrescription transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        MedicationPrescription target = new MedicationPrescription();

        target.setId(source.getId());
        target.setDateWritten(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setStatus(MedicationPrescription.MedicationPrescriptionStatus.ACTIVE);
        target.setPatient(convertPatient(source.getPatient()));
        target.setPrescriber(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setEncounter(getEncounter(container, source.getId()));
        target.setMedication(createMedication(source, target));

        switch (source.getEventType()) {
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

    private Date convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return TransformHelper.toDate(source.getValue());
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

    private Reference getEncounter(FHIRContainer container, String eventId) {
        OpenHR001Encounter encounter = container.getEncounterFromEventId(eventId);
        if (encounter == null)
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounter.getId());
    }

    private Reference createMedication(OpenHR001HealthDomain.Event source, MedicationPrescription target) throws TransformFeatureNotSupportedException {
        Medication medication = new Medication();
        medication.setId("medication");
        medication.setName(source.getDisplayTerm());
        medication.setCode(CodeHelper.convertCode(source.getCode()));

        target.getContained().add(medication);
        return ReferenceHelper.createInternalReference(medication.getId());
    }

    private void convertMedicationIssue(OpenHR001MedicationIssue medicationIssue, MedicationPrescription target) {
        target.addDosageInstruction(new MedicationPrescription.MedicationPrescriptionDosageInstructionComponent()
                .setText(medicationIssue.getDosage())
                .setDose(new Quantity()
                        .setValue(medicationIssue.getQuantity())
                        .setUnits(medicationIssue.getQuantityUnit())));
    }

    private void convertMedication(OpenHR001Medication medication, MedicationPrescription target) {
        target.addDosageInstruction(new MedicationPrescription.MedicationPrescriptionDosageInstructionComponent()
                .setText(medication.getDosage())
                .setDose(new Quantity()
                        .setValue(medication.getQuantity())
                        .setUnits(medication.getQuantityUnit())));
    }

}
