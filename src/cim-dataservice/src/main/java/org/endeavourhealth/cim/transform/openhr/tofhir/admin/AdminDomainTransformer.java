package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;

public class AdminDomainTransformer {
    public static void transform(FHIRContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException {
        OpenHR001AdminDomain adminDomain = openHR.getAdminDomain();

        if (adminDomain == null)
            throw new TransformFeatureNotSupportedException("No AdminDomain element found. Only full patient record supported.");

        OrganisationTransformer.transform(container, adminDomain);
        PractitionerTransformer.transform(container, adminDomain);
        PatientTransformer.transform(container, adminDomain);
    }
}
