package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.FhirConstants;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;

class PatientTransformer {

    public static void transform(FHIRContainer container, OpenHR001AdminDomain adminDomain) throws TransformException {

        OpenHR001Patient sourcePatient = getPatient(adminDomain);
        OpenHR001Person sourcePerson = getPerson(adminDomain.getPerson(), sourcePatient.getId());

        Patient targetPatient = new Patient();

        targetPatient.setId(sourcePatient.getId());

        List<Identifier> identifiers = convertIdentifiers(sourcePatient.getPatientIdentifier());
        if (identifiers != null) {
            identifiers.forEach(targetPatient::addIdentifier);
        }

        List<HumanName> names = NameConverter.convert(
                sourcePerson.getTitle(),
                sourcePerson.getForenames(),
                sourcePerson.getSurname(),
                sourcePerson.getCallingName(),
                sourcePerson.getBirthSurname(),
                sourcePerson.getPreviousSurname());
        if (names != null) names.forEach(targetPatient::addName);

        List<ContactPoint> telecoms = ContactPointConverter.convert(sourcePerson.getContact());
        if (telecoms != null) telecoms.forEach(targetPatient::addTelecom);

        targetPatient.setGender(convertSex(sourcePerson.getSex()));

        targetPatient.setBirthDate(TransformHelper.toDate(sourcePerson.getBirthDate()));

        if (sourcePatient.getDateOfDeath() != null) targetPatient.setDeceased(new DateTimeType(TransformHelper.toDate(sourcePatient.getDateOfDeath())));

        List<Address> addressList = AddressConverter.convertFromPersonAddress(sourcePerson.getAddress());
        if (addressList != null) addressList.forEach(targetPatient::addAddress);

        container.addResource(targetPatient);
    }

    private static Patient.AdministrativeGender convertSex(VocSex sex) throws TransformFeatureNotSupportedException {
        switch (sex) {
            case U:
                return Patient.AdministrativeGender.UNKNOWN;
            case M:
                return Patient.AdministrativeGender.MALE;
            case F:
                return Patient.AdministrativeGender.FEMALE;
            case I:
                return Patient.AdministrativeGender.OTHER;
            default:
                throw new TransformFeatureNotSupportedException("Sex vocabulary of " + sex.toString());
        }
    }

    private List<HumanName> convertName(OpenHR001Person sourcePerson)
    {
        return NameConverter.convert(
                sourcePerson.getTitle(),
                sourcePerson.getForenames(),
                sourcePerson.getSurname(),
                sourcePerson.getCallingName(),
                sourcePerson.getBirthSurname(),
                sourcePerson.getPreviousSurname());
    }

    private static OpenHR001Patient getPatient(OpenHR001AdminDomain adminDomain) throws TransformFeatureNotSupportedException {

        List<OpenHR001Patient> patients = adminDomain.getPatient();

        if (patients == null || patients.isEmpty())
            throw new TransformFeatureNotSupportedException("No AdminDomain.Patients found.");

        if (patients.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single patient supported in AdminDomain.Patients.");

        OpenHR001Patient patient = patients.get(0);

        ToFHIRHelper.ensureDboNotDelete(patient);

        return patient;
    }


    private static OpenHR001Person getPerson(List<OpenHR001Person> sourcePeople, String personId) throws TransformFeatureNotSupportedException, SourceDocumentInvalidException {
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

    private static List<Identifier> convertIdentifiers(List<DtPatientIdentifier> sourceIdentifiers) throws TransformFeatureNotSupportedException {
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
