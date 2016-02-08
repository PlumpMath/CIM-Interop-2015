package org.endeavourhealth.transform.openhr.tofhir;

import org.endeavourhealth.transform.schemas.openhr.*;
import org.endeavourhealth.transform.common.FhirConstants;
import org.endeavourhealth.transform.common.StreamExtension;
import org.endeavourhealth.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.transform.common.TransformHelper;
import org.endeavourhealth.transform.openhr.tofhir.admin.NameConverter;
import org.endeavourhealth.core.text.TextUtils;
import org.hl7.fhir.instance.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ToFHIRHelper {
    public static UUID parseUUID(String id) throws SourceDocumentInvalidException {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new SourceDocumentInvalidException("Could not parse UUID: " + id, e);
        }
    }

    public static void ensureDboNotDelete(DtDbo dbo) throws TransformFeatureNotSupportedException {
        ensureDboNotDelete(dbo.getUpdateMode());
    }

    public static void ensureDboNotDelete(VocUpdateMode updateMode) throws TransformFeatureNotSupportedException {
        if (updateMode == VocUpdateMode.DELETE)
            throw new TransformFeatureNotSupportedException("DBO type of Delete not supported");
    }

    public static DateTimeType convertPartialDateTimeToDateTimeType(DtDatePart source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart()) {
            case Y:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            case YMDT:
                return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.SECOND);
            default:
                throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

    public static DateType convertPartialDateTimeToDateType(DtDatePart source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart()) {
            case Y:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD:
            case YMDT:
                return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            default:
                throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

    public static Range convertAgeRange(DtAgeRange source) throws TransformFeatureNotSupportedException {
        if (source == null)
            return null;

        Range target = new Range();

        //TODO: Age Unit - Consider coding the unit

        if (source.getLow() != null) {
            SimpleQuantity lowQuantity = new SimpleQuantity();
            lowQuantity.setValue(new BigDecimal(source.getLow().getValue()));
            lowQuantity.setUnit(convertAgeUnit(source.getLow().getUnit()));
            target.setLow(lowQuantity);
        }

        if (source.getHigh() != null) {
            SimpleQuantity highQuantity = new SimpleQuantity();
            highQuantity.setValue(new BigDecimal(source.getHigh().getValue()));
            highQuantity.setUnit(convertAgeUnit(source.getHigh().getUnit()));
            target.setHigh(highQuantity);
        }

        return target;
    }

    private static String convertAgeUnit(VocAgeUnit ageUnit) throws TransformFeatureNotSupportedException {
        switch (ageUnit) {
            case D:
                return "day";
            case W:
                return "week";
            case M:
                return "month";
            case Y:
                return "year";
            default:
                throw new TransformFeatureNotSupportedException("Age Unit not supported: " + ageUnit.toString());
        }
    }

    public static OpenHR001Patient getPatient(OpenHR001AdminDomain adminDomain) throws TransformFeatureNotSupportedException {

        List<OpenHR001Patient> patients = adminDomain.getPatient();

        if (patients == null || patients.isEmpty())
            throw new TransformFeatureNotSupportedException("No AdminDomain.Patients found.");

        if (patients.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single patient supported in AdminDomain.Patients.");

        OpenHR001Patient patient = patients.get(0);

        ToFHIRHelper.ensureDboNotDelete(patient);

        return patient;
    }

    public static OpenHR001Person getPerson(List<OpenHR001Person> sourcePeople, String personId) throws TransformFeatureNotSupportedException, SourceDocumentInvalidException {

        if (sourcePeople == null)
            throw new TransformFeatureNotSupportedException("No AdminDomain.Person found.");

        OpenHR001Person person = sourcePeople.stream()
                .filter(p -> p.getId().equals(personId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the person is there multiple times, then it will just throw a general exception.

        if (person == null)
            throw new SourceDocumentInvalidException("Person not found: " + personId);

        ToFHIRHelper.ensureDboNotDelete(person);

        return person;
    }

    public static String getPatientOrganisationGuid(OpenHR001Patient patient) throws TransformFeatureNotSupportedException {

        if (patient == null)
            throw new TransformFeatureNotSupportedException("patient is null");

        if ((patient.getCaseloadPatient() == null) || (patient.getCaseloadPatient().size() == 0))
            throw new TransformFeatureNotSupportedException("caseloadPatient is null or empty");

        OpenHR001CaseloadPatient caseloadPatient = patient.getCaseloadPatient().get(0);

        String organisationGuid = caseloadPatient.getOrganisation();

        if (TextUtils.isNullOrTrimmedEmpty(organisationGuid))
            throw new TransformFeatureNotSupportedException("organisationGuid is null or empty");

        return organisationGuid;
    }

    public static List<HumanName> convertName(OpenHR001Person sourcePerson)
    {
        return NameConverter.convert(
                sourcePerson.getTitle(),
                sourcePerson.getForenames(),
                sourcePerson.getSurname(),
                sourcePerson.getCallingName(),
                sourcePerson.getBirthSurname(),
                sourcePerson.getPreviousSurname());
    }

    public static List<Identifier> convertIdentifiers(List<DtPatientIdentifier> sourceIdentifiers) throws TransformFeatureNotSupportedException {
        if (sourceIdentifiers == null || sourceIdentifiers.isEmpty())
            return null;

        List<Identifier> targetIdentifiers = new ArrayList<>();

        for (DtPatientIdentifier source: sourceIdentifiers) {
            targetIdentifiers.add(new Identifier()
                    .setSystem(convertIdentifierType(source.getIdentifierType()))
                    .setValue(source.getValue()));
        }

        return targetIdentifiers;
    }

    private static String convertIdentifierType(VocPatientIdentifierType openHRType) throws TransformFeatureNotSupportedException {
        switch (openHRType)
        {
            case NHS:
                return FhirConstants.CODE_SYSTEM_NHSNUMBER;
            case ONHS:
                return "http://fhir.endeavourhealth.org/identifier#oldnhsnumber";
            case CHI:
                return "http://fhir.endeavourhealth.org/identifier#chinumber";
            case HOSP:
                return "http://fhir.endeavourhealth.org/identifier#hospitalnumber";
            default:
                throw new TransformFeatureNotSupportedException("VocPatientIdentifierType not supported: " + openHRType.toString());
        }
    }
}
