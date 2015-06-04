package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtContact;
import org.endeavourhealth.cim.transform.schemas.openhr.VocContactType;
import org.hl7.fhir.instance.model.ContactPoint;

import java.util.ArrayList;
import java.util.List;

class ContactPointConverter {
    public static List<ContactPoint> convert(List<DtContact> sourceList) throws TransformFeatureNotSupportedException {
        if (sourceList == null || sourceList.isEmpty())
            return null;

        List<ContactPoint> targetList = new ArrayList<>();

        for (DtContact source: sourceList) {
            ContactPoint target = convertDtContact(source);

            if (target != null)
                targetList.add(target);
        }

        if (targetList.isEmpty())
            return null;
        else
            return targetList;
    }

    private static ContactPoint convertDtContact(DtContact source) throws TransformFeatureNotSupportedException {
        String value = StringUtils.trimToNull(source.getValue());

        if (value == null)
            return null;

        ToFHIRHelper.ensureDboNotDelete(source);

        ContactPoint contact = new ContactPoint();
        contact.setSystem(convertContactSystem(source.getContactType()));
        contact.setUse(convertContactInfoUse(source.getContactType()));
        contact.setValue(value);

        return contact;
    }

    private static ContactPoint.ContactPointSystem convertContactSystem(VocContactType openHRType) throws TransformFeatureNotSupportedException {
        switch (openHRType)
        {
            case H:
            case W:
            case M:
                return ContactPoint.ContactPointSystem.PHONE;
            case FX:
                return ContactPoint.ContactPointSystem.FAX;
            case EM:
                return ContactPoint.ContactPointSystem.EMAIL;
            default:
                throw new TransformFeatureNotSupportedException("VocContactType not supported: " + openHRType.toString());
        }
    }

    private static ContactPoint.ContactPointUse convertContactInfoUse(VocContactType openHRType) throws TransformFeatureNotSupportedException {
        switch (openHRType)
        {
            case H:
            case EM:
                return ContactPoint.ContactPointUse.HOME;
            case W:
            case FX:
                return ContactPoint.ContactPointUse.WORK;
            case M:
                return ContactPoint.ContactPointUse.MOBILE;
            default:
                throw new TransformFeatureNotSupportedException("VocContactType not supported: " + openHRType.toString());
        }
    }

}
