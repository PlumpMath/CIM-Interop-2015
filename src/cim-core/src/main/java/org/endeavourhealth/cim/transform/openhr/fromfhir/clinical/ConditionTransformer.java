package org.endeavourhealth.cim.transform.openhr.fromfhir.clinical;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.OpenHRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.hl7.fhir.instance.model.Coding;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Reference;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class ConditionTransformer {
    public static void transform(OpenHRContainer container, Condition condition) throws TransformException
    {
        if (TextUtils.isNullOrTrimmedEmpty(condition.getId()))
            condition.setId(UUID.randomUUID().toString());

        container.getEvents().add(createEvent(condition));
        container.getProblems().add(createProblem(condition));
    }

    private static OpenHR001Problem createProblem(Condition condition)
    {
        OpenHR001Problem targetProblem = new OpenHR001Problem();
        targetProblem.setId(condition.getId());
        targetProblem.setUpdateMode(VocUpdateMode.ADD);

        targetProblem.setStatus(VocProblemStatus.A);
        targetProblem.setSignificance(VocProblemSignificance.S);

        return targetProblem;
    }

    private static OpenHR001HealthDomain.Event createEvent(Condition condition) throws TransformException
    {
        OpenHR001HealthDomain.Event targetEvent = new OpenHR001HealthDomain.Event();

        targetEvent.setId(condition.getId());
        targetEvent.setUpdateMode(VocUpdateMode.ADD);

        if (condition.getPatient() != null)
            targetEvent.setPatient(getPatientIdFromReference(condition.getPatient()));

        targetEvent.setEventType(VocEventType.OBS);

        if (condition.getDateRecorded() != null)
        {
            XMLGregorianCalendar eventDate;
            try
            {
                eventDate = TransformHelper.fromDate(condition.getDateRecorded());
            } catch (DatatypeConfigurationException e)
            {
                throw new SourceDocumentInvalidException("Error creating Date", e);
            }

            DtDatePart effectiveTime = new DtDatePart();
            effectiveTime.setValue(eventDate);
            effectiveTime.setDatepart(VocDatePart.YMD);
            targetEvent.setEffectiveTime(effectiveTime);
            targetEvent.setAvailabilityTimeStamp(eventDate);
        } else
        {
            DtDatePart effectiveTime = new DtDatePart();
            effectiveTime.setValue(null);
            effectiveTime.setDatepart(VocDatePart.U);
            targetEvent.setEffectiveTime(effectiveTime);
            targetEvent.setAvailabilityTimeStamp(ToFHIRHelper.toCalendar(new Date()));
        }

        if (condition.getAsserter() != null)
        {
            targetEvent.setAuthorisingUserInRole(getPractitionerIdFromReference(condition.getAsserter()));
            targetEvent.setEnteredByUserInRole(getPractitionerIdFromReference(condition.getAsserter()));
        }

        if (condition.getCode() != null)
        {
            for (Coding sourceCode : condition.getCode().getCoding())
            {
                DtCodeQualified targetCode = new DtCodeQualified();
                targetCode.setCodeSystem(convertCodeSystem(sourceCode.getSystem()));
                targetCode.setCode(sourceCode.getCode());
                targetCode.setDisplayName(sourceCode.getDisplay());

                if (targetEvent.getCode() == null)
                    targetEvent.setCode(targetCode);
                else
                    targetEvent.getCode().getTranslation().add(targetCode);
            }
        }

        if (StringUtils.isNotBlank(condition.getNotes()))
        {
            OpenHR001Event.AssociatedText associatedText = new OpenHR001Event.AssociatedText();
            associatedText.setAssociatedTextType(VocAssociatedTextType.PT);
            associatedText.setValue(condition.getNotes());
            targetEvent.getAssociatedText().add(associatedText);
        }

        OpenHR001Observation observation = new OpenHR001Observation();
        observation.setEpisodicity(VocEpisodicity.NEW);
        targetEvent.setObservation(observation);

        return targetEvent;
    }

    private static String getPatientIdFromReference(Reference reference) {
        return StringUtils.removeStart(reference.getReference(), "Patient/");
    }

    private static String getPractitionerIdFromReference(Reference reference) {
        return StringUtils.removeStart(reference.getReference(), "Practitioner/");
    }

    private static String convertCodeSystem(String codeSystem) throws TransformFeatureNotSupportedException {

        final String snomedOid = "2.16.840.1.113883.2.1.3.2.4.15";

        switch (codeSystem)
        {
            case "READ2":
                return "2.16.840.1.113883.2.1.6.2";
            case "SNOMED":
                return snomedOid;
            case "http://snomed.info/sct":
                return snomedOid;
            default:
                throw new TransformFeatureNotSupportedException("CodeSystem not supported: " + codeSystem);
        }
    }

}
