package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.Results;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

public class AdminDomainTransformer {
    public static void transform(Results results, OpenHR001OpenHealthRecord openHR) throws TransformException {
        OpenHR001AdminDomain adminDomain = openHR.getAdminDomain();

        if (adminDomain == null)
            throw new TransformFeatureNotSupportedException("No AdminDomain element found. Only full patient record supported.");

        OrganisationTransformer.transform(results, adminDomain);
        PractitionerTransformer.transform(results, adminDomain);
        PatientTransformer.transform(results, adminDomain);
    }
}
