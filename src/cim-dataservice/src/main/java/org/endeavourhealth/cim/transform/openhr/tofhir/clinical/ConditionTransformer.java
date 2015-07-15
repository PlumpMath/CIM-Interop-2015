package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.List;
import java.util.Map;

class ConditionTransformer {
    public static Condition transform(OpenHR001HealthDomain healthDomain,
                                      FHIRContainer container,
                                      OpenHR001HealthDomain.Event source,
                                      OpenHR001Problem problem) throws TransformException {
        Condition target = new Condition();
        target.setId(convertId(source.getId()));
        target.setPatient(convertPatient(source.getPatient()));
        target.setEncounter(getEncounter(container.getEventEncouterMap(), source.getId()));
        target.setAsserter(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setDateAssertedElement(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setCode(convertCode(source.getCode()));
        target.setCategory(convertCategory());
        target.setSeverity(convertSeverity(problem.getSignificance()));

        handleAssociatedText(source, target);

        return target;
    }

    private static String convertId(String sourceId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourceId))
            throw new SourceDocumentInvalidException("Invalid Event Id");
        return sourceId;
    }

    private static Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private static Reference getEncounter(Map<String, String> eventEncouterMap, String eventId) {
        String encounterId = eventEncouterMap.get(eventId);
        if (StringUtils.isBlank(encounterId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounterId);
    }

    private static Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private static DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return ToFHIRHelper.convertPartialDateTimeToDateType(source);
    }

    private static CodeableConcept convertCode(DtCodeQualified sourceCode) throws TransformFeatureNotSupportedException {
        if (sourceCode == null)
            return null;

        CodeableConcept result = new CodeableConcept();
        addCode(result, sourceCode);
        return result;
    }

    private static void addCode(CodeableConcept codeableConcept, DtCodeQualified sourceCode) throws TransformFeatureNotSupportedException {
        codeableConcept.addCoding(new Coding()
                .setCode(sourceCode.getCode())
                .setDisplay(sourceCode.getDisplayName())
                .setSystem(convertCodeSystem(sourceCode.getCodeSystem())));

        if (sourceCode.getTranslation() != null) {
            for (DtCodeQualified code : sourceCode.getTranslation())
                addCode(codeableConcept, code);
        }
    }

    private static String convertCodeSystem(String sourceCodeSystem) throws TransformFeatureNotSupportedException {
        switch (sourceCodeSystem) {
            case "2.16.840.1.113883.2.1.6.2":
                return "READ2";
            case "2.16.840.1.113883.2.1.3.2.4.15":
                return "SNOMED";
            case "2.16.840.1.113883.2.1.6.3":
                return "http://www.e-mis.com/emisopen/emis_snomed";
            default:
                throw new TransformFeatureNotSupportedException("CodeSystem not supported: " + sourceCodeSystem);
        }
    }

    private static CodeableConcept convertCategory() throws TransformFeatureNotSupportedException {
        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://hl7.org/fhir/condition-status")
                                .setCode("confirmed")
                                .setDisplay("Confirmed")
                );
    }

    private static CodeableConcept convertSeverity(VocProblemSignificance sourceSignificance) throws TransformFeatureNotSupportedException {
        Coding coding = new Coding();
        coding.setSystem("http://hl7.org/fhir/vs/condition-severity");

        switch (sourceSignificance) {
            case M:
                coding.setCode("255604002");
                coding.setDisplay("Mild");
                break;
            case S:
                coding.setCode("24484000");
                coding.setDisplay("Severe");
                break;
            default:
                throw new TransformFeatureNotSupportedException("VocProblemSignificance not supported: " + sourceSignificance);
        }

        return new CodeableConcept().addCoding(coding);
    }

    private static void handleAssociatedText(OpenHR001Event source, Condition target) throws SourceDocumentInvalidException {

        List<OpenHR001Event.AssociatedText> associatedTextList = source.getAssociatedText();

        if (associatedTextList == null)
            return;

        long distinctCount = associatedTextList.stream()
                .map(a -> a.getAssociatedTextType())
                .distinct()
                .count();

        if (distinctCount != associatedTextList.size())
            throw new SourceDocumentInvalidException("AssociatedText contains duplicate types");

        for (OpenHR001Event.AssociatedText associatedText: source.getAssociatedText()) {

            VocAssociatedTextType type = associatedText.getAssociatedTextType();
            String value = StringUtils.trimToNull(associatedText.getValue());

            if (value == null)
                continue;

            if (type == VocAssociatedTextType.POST) {
                target.setNotes(value);
            } else {
                //TODO: Add Extension
            }
        }
    }
}