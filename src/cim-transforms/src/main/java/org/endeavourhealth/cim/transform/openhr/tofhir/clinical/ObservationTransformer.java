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

class ObservationTransformer implements ClinicalResourceTransformer {
    private final static String CERTAINTY_QUALIFIER_NAME = "Certainty";
    private final static String UNCERTAIN_QUALIFIER_VALUE = "Uncertain";

    public Resource transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        Observation target = new Observation();
        target.setId(source.getId());
        target.setCode(CodeConverter.convertCode(source.getCode(), source.getDisplayTerm()));
        target.setApplies(convertEffectiveDateTime(source.getEffectiveTime()));
        target.setIssued(TransformHelper.toDate(source.getAvailabilityTimeStamp()));
        target.setStatus(Observation.ObservationStatus.FINAL);
        target.setReliability(convertReliability(source.getCode()));
        target.setSubject(convertPatient(source.getPatient()));
        target.addPerformer(convertUserInRole(source.getAuthorisingUserInRole()));
        target.setEncounter(getEncounter(container.getEventEncounterMap(), source.getId()));

        convertAssociatedText(source, target);

        // process observation event specific data
        OpenHR001Observation sourceObservation = source.getObservation();
        if (sourceObservation != null) {
            target.setInterpretation(convertAbnormality(sourceObservation));
            convertValue(sourceObservation.getValue(), target);
            addRelatedObservations(sourceObservation.getComplexObservation(), source.getCode(), target);
        }

        return target;
    }

    private static DateType convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return ToFHIRHelper.convertPartialDateTimeToDateType(source);
    }

    private static Reference convertPatient(String sourcePatientId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(sourcePatientId))
            throw new SourceDocumentInvalidException("Invalid Patient Id");
        return ReferenceHelper.createReference(ResourceType.Patient, sourcePatientId);
    }

    private static Reference convertUserInRole(String userInRoleId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(userInRoleId))
            throw new SourceDocumentInvalidException("UserInRoleId not found");

        return ReferenceHelper.createReference(ResourceType.Practitioner, userInRoleId);
    }

    private static Reference getEncounter(Map<String, String> eventEncounterMap, String eventId) {
        String encounterId = eventEncounterMap.get(eventId);
        if (StringUtils.isBlank(encounterId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Encounter, encounterId);
    }

    private static Observation.ObservationReliability convertReliability(DtCodeQualified sourceCode) {
        return CodeConverter.hasQualifier(sourceCode, CERTAINTY_QUALIFIER_NAME, UNCERTAIN_QUALIFIER_VALUE)
                ? Observation.ObservationReliability.QUESTIONABLE
                : Observation.ObservationReliability.OK;
    }

    private static void convertAssociatedText(OpenHR001Event source, Observation target) throws SourceDocumentInvalidException {

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
                target.setComments(value);
            } else {
                //TODO: Add Extension for non post text associated text
            }
        }
    }

    private static CodeableConcept convertAbnormality(OpenHR001Observation source)
    {
        if (source == null || source.getAbnormal() == null || !source.getAbnormal().isValue())
            return null;

        CodeableConcept result = new CodeableConcept()
                .setText(source.getAbnormal().getDescription());

        switch (source.getAbnormal().getDescription().toUpperCase())
        {
            //High
            case "HIGH":
            case "HI":
            case "SH":
            case "H":
            case "+":
                result.addCoding(new Coding()
                        .setSystem("http://hl7.org/fhir/v2/0078")
                        .setCode("H")
                        .setDisplay("Above high normal"));
                break;
            //Low
            case "LOW":
            case "LO":
            case "SL":
            case "L":
            case "-":
                result.addCoding(new Coding()
                        .setSystem("http://hl7.org/fhir/v2/0078")
                        .setCode("L")
                        .setDisplay("Below low normal"));
                break;
            //Outside Reference Range
            case "OR":
            //Probably Abnormal
            case "PA":
            default:
                result.addCoding(new Coding()
                        .setSystem("http://hl7.org/fhir/v2/0078")
                        .setCode("A")
                        .setDisplay("Abnormal"));
                break;
        }

        return result;
    }

    private static void convertValue(OpenHR001ObservationValue sourceValue, Observation target) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        if (sourceValue == null)
            return;

        if (sourceValue.getNumeric() != null) {
            target.setValue(new Quantity()
                    .setValue(sourceValue.getNumeric().getValue())
                    .setUnits(sourceValue.getNumeric().getUnits())
                    .setComparator(convertQuantityComparator(sourceValue.getNumeric().getOperator())));
            target.addReferenceRange(convertRange(sourceValue.getNumeric().getRange()));
        }
        else if (sourceValue.getText() != null) {
            target.setValue(new StringType()
                    .setValue(sourceValue.getText().getValue()));
            target.addReferenceRange(convertRange(sourceValue.getText().getRange()));
        }
        else
            throw new SourceDocumentInvalidException("Invalid Observation Value: Missing Numeric and Text Value");
    }

    private static Quantity.QuantityComparator convertQuantityComparator(String operator) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(operator))
            return null;

        switch (operator) {
            case ">":
                return Quantity.QuantityComparator.GREATER_THAN;
            case ">=":
                return Quantity.QuantityComparator.GREATER_OR_EQUAL;
            case "<":
                return Quantity.QuantityComparator.LESS_THAN;
            case "<=":
                return Quantity.QuantityComparator.GREATER_OR_EQUAL;
            default:
                throw new SourceDocumentInvalidException("Invalid Numeric Operator: " + operator);
        }
    }

    private static Observation.ObservationReferenceRangeComponent convertRange(OpenHR001Range sourceRange) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        if (sourceRange == null)
            return null;

        Observation.ObservationReferenceRangeComponent rangeComponent = new Observation.ObservationReferenceRangeComponent();

        if (sourceRange.getNumericRange() != null) {
            rangeComponent.setLow(convertNumericRange(sourceRange.getNumericRange().getLow(), sourceRange.getUnits()));
            rangeComponent.setHigh(convertNumericRange(sourceRange.getNumericRange().getHigh(), sourceRange.getUnits()));
        }
        else if (sourceRange.getTextRange() != null) {
            //TODO: Text Range - Text range High and Low values can not be mapped, consider extension
        }
        else {
            rangeComponent.setText(sourceRange.getDescription());
        }

        rangeComponent.setAge(ToFHIRHelper.convertAgeRange(sourceRange.getAgeRange()));

        return rangeComponent;
    }

    private static Quantity convertNumericRange(OpenHR001RangeValue rangeValue, String units) throws SourceDocumentInvalidException {
        if (rangeValue == null)
            return null;

        return new Quantity()
                .setValue(rangeValue.getValue())
                .setUnits(units)
                .setComparator(convertQuantityComparator(rangeValue.getOperator()));
    }

    private static void addRelatedObservations(OpenHR001ComplexObservation complexObservation, DtCodeQualified sourceCode, Observation target) {
        if (complexObservation == null)
            return;

        Observation.ObservationRelationshiptypes relationshipType = CodeConverter.isBloodPressureCode(sourceCode)
                ? Observation.ObservationRelationshiptypes.HASCOMPONENT
                : Observation.ObservationRelationshiptypes.HASMEMBER;
        
        for (String relatedObservationId: complexObservation.getChildLink()) {
            target.addRelated(new Observation.ObservationRelatedComponent()
                    .setTarget(ReferenceHelper.createReference(ResourceType.Observation, relatedObservationId))
                    .setType(relationshipType));
        }
    }
}
