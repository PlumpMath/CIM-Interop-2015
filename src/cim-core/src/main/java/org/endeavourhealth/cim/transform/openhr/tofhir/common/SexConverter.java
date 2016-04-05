package org.endeavourhealth.cim.transform.openhr.tofhir.common;

import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.VocSex;
import org.hl7.fhir.instance.model.Enumerations;

public class SexConverter
{
    public static Enumerations.AdministrativeGender convertSex(VocSex sex) throws TransformFeatureNotSupportedException
    {
        switch (sex)
        {
            case U: return Enumerations.AdministrativeGender.UNKNOWN;
            case M: return Enumerations.AdministrativeGender.MALE;
            case F: return Enumerations.AdministrativeGender.FEMALE;
            case I: return Enumerations.AdministrativeGender.OTHER;
            default: throw new TransformFeatureNotSupportedException("Sex vocabulary of " + sex.toString());
        }
    }
}
