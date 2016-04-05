package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.SexConverter;
import org.endeavourhealth.cim.transform.schemas.openhr.VocSex;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Patient;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Person;
import org.hl7.fhir.instance.model.*;

import java.util.List;

public class PersonTransformer
{
    public static Person transform(OpenHR001AdminDomain adminDomain) throws TransformException
    {

        OpenHR001Patient sourcePatient = OpenHRHelper.getPatient(adminDomain);
        OpenHR001Person sourcePerson = OpenHRHelper.getPerson(adminDomain.getPerson(), sourcePatient.getId());

        Person targetPerson = new Person();

        targetPerson.setId(sourcePatient.getId());

        List<Identifier> identifiers = OpenHRHelper.convertIdentifiers(sourcePatient.getPatientIdentifier());

        if (identifiers != null)
            for (Identifier identifier : identifiers)
                targetPerson.addIdentifier(identifier);

        List<HumanName> names = OpenHRHelper.convertName(sourcePerson);

        for (HumanName name : names)
            targetPerson.addName(name);

        targetPerson.setGender(SexConverter.convertSex(sourcePerson.getSex()));
        targetPerson.setBirthDate(TransformHelper.toDate(sourcePerson.getBirthDate()));

        Person.PersonLinkComponent link = targetPerson.addLink();
        link.setTarget(ReferenceHelper.createReference(ResourceType.Patient, sourcePatient.getId()));

        return targetPerson;
    }
}
