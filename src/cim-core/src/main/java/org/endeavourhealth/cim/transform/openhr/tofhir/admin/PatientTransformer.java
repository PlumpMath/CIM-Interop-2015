package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.AddressConverter;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.ContactPointConverter;
import org.hl7.fhir.instance.model.*;
import org.hl7.fhir.instance.model.Enumerations.AdministrativeGender;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Patient;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Person;
import org.endeavourhealth.cim.transform.schemas.openhr.VocSex;

import java.util.List;

public class PatientTransformer
{
    public static Patient transform(OpenHR001AdminDomain adminDomain) throws TransformException
    {
        OpenHR001Patient sourcePatient = OpenHRHelper.getPatient(adminDomain);
        OpenHR001Person sourcePerson = OpenHRHelper.getPerson(adminDomain.getPerson(), sourcePatient.getId());

        Patient targetPatient = new Patient();

        targetPatient.setId(sourcePatient.getId());

        List<Identifier> identifiers = OpenHRHelper.convertIdentifiers(sourcePatient.getPatientIdentifier());

        if (identifiers != null)
            identifiers.forEach(targetPatient::addIdentifier);

        List<HumanName> names = OpenHRHelper.convertName(sourcePerson);

        if (names != null)
            names.forEach(targetPatient::addName);

        List<ContactPoint> telecoms = ContactPointConverter.convert(sourcePerson.getContact());

        if (telecoms != null)
            telecoms.forEach(targetPatient::addTelecom);

        AdministrativeGender gender = convertSex(sourcePerson.getSex());
        targetPatient.setGender(gender);

        targetPatient.setBirthDate(TransformHelper.toDate(sourcePerson.getBirthDate()));

        if (sourcePatient.getDateOfDeath() != null)
            targetPatient.setDeceased(new DateTimeType(TransformHelper.toDate(sourcePatient.getDateOfDeath())));

        List<Address> addressList = AddressConverter.convertFromPersonAddress(sourcePerson.getAddress());

        if (addressList != null)
            addressList.forEach(targetPatient::addAddress);

        String organisationGuid = OpenHRHelper.getPatientOrganisationGuid(sourcePatient);

        targetPatient.setManagingOrganization(ReferenceHelper.createReference(ResourceType.Organization, organisationGuid));

        return targetPatient;
    }

    private static AdministrativeGender convertSex(VocSex sex) throws TransformFeatureNotSupportedException
    {
        switch (sex)
        {
            case U:
                return AdministrativeGender.UNKNOWN;
            case M:
                return AdministrativeGender.MALE;
            case F:
                return AdministrativeGender.FEMALE;
            case I:
                return AdministrativeGender.OTHER;
            default:
                throw new TransformFeatureNotSupportedException("Sex vocabulary of " + sex.toString());
        }
    }
}
