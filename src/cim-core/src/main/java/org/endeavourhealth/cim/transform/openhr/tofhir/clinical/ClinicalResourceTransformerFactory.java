package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Problem;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.DtCodeQualified;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.VocEventType;

import java.util.List;

class ClinicalResourceTransformerFactory {
    public static ClinicalResourceTransformer getTransformerForEvent(OpenHR001HealthDomain healthDomain, OpenHR001HealthDomain.Event event) throws TransformException
    {

        if (isCondition(healthDomain.getProblem(), event))
            return new ConditionTransformer();

        switch (event.getEventType()) {
            case OBS: // Observation
                if (isProcedure(event))
                    return new ProcedureTransformer();
                else
                    return new ObservationTransformer();
            case VAL: // Value
            case INV: // Investigation
            case ATT: // Attachment
            case DRY: // Diary
                return new ObservationTransformer();
            case ISS: // Medication Issue
            case MED: // Medication
                return new MedicationTransformer();
            case TR: // Test Request
                return new DiagnosticOrderTransformer();
            case REF: // Referral
                return new ReferralTransformer();
            case ALT: // Alert
                return new AlertTransformer();
            case ALL: // Allergy
                return new AllergyTransformer();
            case FH: // Family history
                return new FamilyHistoryTransformer();
            case IMM: // Immunisation
                return new ImmunisationTransformer();
            case REP: // Report
                return new DiagnosticReportTransformer();
            default:
                throw new TransformFeatureNotSupportedException("Event Type not supported: " + event.getEventType().toString());
        }
    }

    private static boolean isCondition(List<OpenHR001Problem> problemList, OpenHR001HealthDomain.Event event) throws SourceDocumentInvalidException
    {
        // The condition resource specifically excludes AllergyIntolerance as those are handled with their own resource
        return event.getEventType() != VocEventType.ALL
                && problemList != null
                && problemList.stream().anyMatch(p -> p.getId().equals(event.getId()));
    }

    private static boolean isProcedure(OpenHR001HealthDomain.Event event) throws SourceDocumentInvalidException {
        //TODO: Temporary hack - This method needs to be replaced with SNOMED hierarchy lookup using CREs

        if (event.getCode() != null) {
            if (isProcedureCode(event.getCode()))
                return true;

            if (event.getCode().getTranslation() != null) {
                if (event.getCode().getTranslation()
                        .stream()
                        .anyMatch(t -> isProcedureCode(t)))
                    return true;
            }
        }

        return false;
    }

    private static boolean isProcedureCode(DtCodeQualified code) {
        // check for codes in the READ2 code system that fall in the Procedure (7) hierarchy
        return code != null && code.getCodeSystem().equals("2.16.840.1.113883.2.1.6.2") && code.getCode().startsWith("7");
    }
}
