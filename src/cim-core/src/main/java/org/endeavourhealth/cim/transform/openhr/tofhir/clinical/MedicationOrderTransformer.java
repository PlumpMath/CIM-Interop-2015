package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.CodeHelper;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.SexConverter;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.VocDrugIssueType;
import org.hl7.fhir.instance.model.*;

import java.math.BigInteger;

public class MedicationOrderTransformer implements ClinicalResourceTransformer
{
    public MedicationOrder transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        MedicationOrder target = new MedicationOrder();

        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_MEDICATION_ORDER));

        target.setDateWrittenElement(OpenHRHelper.convertPartialDateTimeToDateTimeType(source.getEffectiveTime()));

        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));
        target.setPrescriber(ReferenceHelper.createReference(ResourceType.Practitioner, source.getAuthorisingUserInRole()));

        target.addDosageInstruction(getDosage(source));

        Reference encounterReference = eventEncounterMap.getEncounterReference(source.getId());

        if (encounterReference != null)
            target.setEncounter(encounterReference);

        target.setMedication(CodeHelper.convertCode(source.getCode()));

        target.setDispenseRequest(getDispenseRequest(source));



        return target;
    }

    private MedicationOrder.MedicationOrderDosageInstructionComponent getDosage(OpenHR001HealthDomain.Event source)
    {
        return new MedicationOrder.MedicationOrderDosageInstructionComponent()
                .setText(source.getMedicationIssue().getDosage());
    }

    private MedicationOrder.MedicationOrderDispenseRequestComponent getDispenseRequest(OpenHR001HealthDomain.Event source)
    {
        SimpleQuantity quantity = new SimpleQuantity();
        quantity.setValue(source.getMedicationIssue().getQuantity());
        quantity.setUnit(source.getMedicationIssue().getQuantityUnit());

        return new MedicationOrder.MedicationOrderDispenseRequestComponent()
                .setQuantity(quantity);
    }

    private Extension getMedicationSupplyTypeExtension(OpenHR001HealthDomain.Event source)
    {
        // convert this to enum / object list

        final int SUPPLYTYPE_NHS_PRESCRIPTION = 394823007;
        final String SUPPLYTYPE_NHS_PRESCRIPTION_TEXT = "NHS Prescription";

        final int SUPPLYTYPE_NHS_PRIVATE_PRESCRIPTION = 394824001;
        final String SUPPLYTYPE_NHS_PRIVATE_PRESCRIPTION_TEXT = "Private Prescription";

        final int SUPPLYTYPE_NHS_ACBS_PRESCRIPTION = 394825000;
        final String SUPPLYTYPE_NHS_ACBS_PRESCRIPTION_TEXT = "ACBS Prescription";

        final int SUPPLYTYPE_NHS_OTC_SALE = 394826004;
        final String SUPPLYTYPE_NHS_OTC_SALE_TEXT = "OTC sale";

        final int SUPPLYTYPE_NHS_PERSONAL_ADMINISTRATION = 394827008;
        final String SUPPLYTYPE_NHS_PERSONAL_ADMINISTRATION_TEXT = "Personal Administration";

        final int SUPPLYTYPE_NHS_PRESCRIBED_BY_OTHER_ORGANISATION = 394828003;
        final String SUPPLYTYPE_NHS_PRESCRIBED_BY_OTHER_ORGANISATION_TEXT = "Prescription by another organisation";

        final int SUPPLYTYPE_NHS_PRESCRIBED_PAST_MEDICATION = 394829006;
        final String SUPPLYTYPE_NHS_PRESCRIBED_PAST_MEDICATION_TEXT = "Past medication";

        return null;
    }
}