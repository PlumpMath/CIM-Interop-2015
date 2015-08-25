package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Patient;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Person;
import org.endeavourhealth.cim.transform.schemas.openhr.VocSex;
import org.hl7.fhir.instance.model.*;

import java.util.List;

public class PersonTransformer {

    public static Person transform(OpenHR001AdminDomain adminDomain) throws TransformException {

        OpenHR001Patient sourcePatient = ToFHIRHelper.getPatient(adminDomain);
        OpenHR001Person sourcePerson = ToFHIRHelper.getPerson(adminDomain.getPerson(), sourcePatient.getId());

        Person targetPerson = new Person();

        targetPerson.setId(sourcePatient.getId());

        List<Identifier> identifiers = ToFHIRHelper.convertIdentifiers(sourcePatient.getPatientIdentifier());

        if (identifiers != null)
            for (Identifier identifier : identifiers)
                targetPerson.addIdentifier(identifier);

        List<HumanName> names = ToFHIRHelper.convertName(sourcePerson);

        for (HumanName name : names)
            targetPerson.addName(name);

        targetPerson.setGender(convertSex(sourcePerson.getSex()));
        targetPerson.setBirthDate(TransformHelper.toDate(sourcePerson.getBirthDate()));

        return targetPerson;
    }

    private static Person.AdministrativeGender convertSex(VocSex sex) throws TransformFeatureNotSupportedException {
        switch (sex) {
            case U:
                return Person.AdministrativeGender.UNKNOWN;
            case M:
                return Person.AdministrativeGender.MALE;
            case F:
                return Person.AdministrativeGender.FEMALE;
            case I:
                return Person.AdministrativeGender.OTHER;
            default:
                throw new TransformFeatureNotSupportedException("Sex vocabulary of " + sex.toString());
        }
    }
}
