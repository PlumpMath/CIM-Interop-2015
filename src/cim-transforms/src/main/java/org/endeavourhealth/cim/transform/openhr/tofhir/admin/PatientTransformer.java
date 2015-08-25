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

        OpenHR001Patient sourcePatient = ToFHIRHelper.getPatient(adminDomain);
        OpenHR001Person sourcePerson = ToFHIRHelper.getPerson(adminDomain.getPerson(), sourcePatient.getId());

        Patient targetPatient = new Patient();

        targetPatient.setId(sourcePatient.getId());

        List<Identifier> identifiers = ToFHIRHelper.convertIdentifiers(sourcePatient.getPatientIdentifier());
        if (identifiers != null) {
            identifiers.forEach(targetPatient::addIdentifier);
        }

        List<HumanName> names = ToFHIRHelper.convertName(sourcePerson);
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
}
