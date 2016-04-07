package org.endeavourhealth.cim.transform.common;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.NameConverter;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.hl7.fhir.instance.model.*;

import java.math.BigDecimal;
import java.util.*;

public class OpenHRHelper
{
    public static void ensureDboNotDelete(DtDbo dbo) throws TransformFeatureNotSupportedException
    {
        ensureDboNotDelete(dbo.getUpdateMode());
    }

    public static void ensureDboNotDelete(VocUpdateMode updateMode) throws TransformFeatureNotSupportedException
    {
        if (updateMode == VocUpdateMode.DELETE)
            throw new TransformFeatureNotSupportedException("DBO type of Delete not supported");
    }

    public static DateTimeType convertPartialDateTimeToDateTimeType(DtDatePart source) throws TransformException
    {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart())
        {
            case Y: return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM: return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD: return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            case YMDT: return new DateTimeType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.SECOND);
            default: throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

    public static DateType convertPartialDateTimeToDateType(DtDatePart source) throws TransformException
    {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        if (source.getDatepart() == VocDatePart.U)
            return null;

        switch (source.getDatepart())
        {
            case Y: return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.YEAR);
            case YM: return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.MONTH);
            case YMD:
            case YMDT: return new DateType(TransformHelper.toDate(source.getValue()), TemporalPrecisionEnum.DAY);
            default: throw new TransformFeatureNotSupportedException("Date part not supported: " + source.getDatepart().toString());
        }
    }

    public static Range convertAgeRange(DtAgeRange source) throws TransformFeatureNotSupportedException
    {
        if (source == null)
            return null;

        Range target = new Range();

        //TODO: Age Unit - Consider coding the unit

        if (source.getLow() != null)
        {
            SimpleQuantity lowQuantity = new SimpleQuantity();
            lowQuantity.setValue(new BigDecimal(source.getLow().getValue()));
            lowQuantity.setUnit(convertAgeUnit(source.getLow().getUnit()));
            target.setLow(lowQuantity);
        }

        if (source.getHigh() != null)
        {
            SimpleQuantity highQuantity = new SimpleQuantity();
            highQuantity.setValue(new BigDecimal(source.getHigh().getValue()));
            highQuantity.setUnit(convertAgeUnit(source.getHigh().getUnit()));
            target.setHigh(highQuantity);
        }

        return target;
    }

    private static String convertAgeUnit(VocAgeUnit ageUnit) throws TransformFeatureNotSupportedException
    {
        switch (ageUnit)
        {
            case D: return "day";
            case W: return "week";
            case M: return "month";
            case Y: return "year";
            default: throw new TransformFeatureNotSupportedException("Age Unit not supported: " + ageUnit.toString());
        }
    }

    public static OpenHR001Patient getPatient(OpenHR001AdminDomain adminDomain) throws TransformFeatureNotSupportedException
    {
        List<OpenHR001Patient> patients = adminDomain.getPatient();

        if (patients == null || patients.isEmpty())
            throw new TransformFeatureNotSupportedException("No AdminDomain.Patients found.");

        if (patients.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single patient supported in AdminDomain.Patients.");

        OpenHR001Patient patient = patients.get(0);

        OpenHRHelper.ensureDboNotDelete(patient);

        return patient;
    }

    public static OpenHR001Person getPerson(List<OpenHR001Person> sourcePeople, String personId) throws TransformFeatureNotSupportedException, SourceDocumentInvalidException
    {
        if (sourcePeople == null)
            throw new TransformFeatureNotSupportedException("No AdminDomain.Person found.");

        OpenHR001Person person = sourcePeople.stream()
                .filter(p -> p.getId().equals(personId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the person is there multiple times, then it will just throw a general exception.

        if (person == null)
            throw new SourceDocumentInvalidException("Person not found: " + personId);

        OpenHRHelper.ensureDboNotDelete(person);

        return person;
    }

    public static String getPatientOrganisationGuid(OpenHR001Patient patient) throws TransformFeatureNotSupportedException
    {
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

    public static List<Identifier> convertIdentifiers(List<DtPatientIdentifier> sourceIdentifiers) throws TransformFeatureNotSupportedException
    {
        if (sourceIdentifiers == null || sourceIdentifiers.isEmpty())
            return null;

        List<Identifier> targetIdentifiers = new ArrayList<>();

        for (DtPatientIdentifier source: sourceIdentifiers)
            if ((source.getIdentifierType() == VocPatientIdentifierType.NHS) || (source.getIdentifierType() == VocPatientIdentifierType.CHI))
                if (!StringUtils.isBlank(source.getValue()))
                    targetIdentifiers.add(TransformHelper.createIdentifier(convertIdentifierType(source.getIdentifierType()), source.getValue()));

        return targetIdentifiers;
    }

    private static String convertIdentifierType(VocPatientIdentifierType openHRType) throws TransformFeatureNotSupportedException
    {
        switch (openHRType)
        {
            case NHS: return FhirUris.IDENTIFIER_SYSTEM_NHSNUMBER;
            case CHI: return FhirUris.IDENTIFIER_SYSTEM_CHINUMBER;
            default: throw new TransformFeatureNotSupportedException("VocPatientIdentifierType not supported: " + openHRType.toString());
        }
    }

    public static boolean isProblem(List<OpenHR001Problem> problemList, OpenHR001HealthDomain.Event event) throws SourceDocumentInvalidException
    {
        // The condition resource specifically excludes AllergyIntolerance as those are handled with their own resource
        return event.getEventType() != VocEventType.ALL
                && problemList != null
                && problemList.stream().anyMatch(p -> p.getId().equals(event.getId()));
    }

    public static boolean isProcedure(OpenHR001HealthDomain.Event event) throws SourceDocumentInvalidException
    {
        //TODO: This method needs to be replaced with SNOMED hierarchy lookup using CREs

        if (event.getCode() != null)
        {
            if (isProcedureCode(event.getCode()))
                return true;

            if (event.getCode().getTranslation() != null)
            {
                return (event
                        .getCode()
                        .getTranslation()
                        .stream()
                        .anyMatch(t -> isProcedureCode(t)));
            }
        }

        return false;
    }

    public static boolean isProcedureCode(DtCodeQualified code)
    {
        // check for codes in the READ2 code system that fall in the Procedure (7) hierarchy
        return code != null && code.getCodeSystem().equals("2.16.840.1.113883.2.1.6.2") && code.getCode().startsWith("7");
    }
}
