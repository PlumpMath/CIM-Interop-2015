package org.endeavourhealth.transform.openhr.tofhir.admin;

import org.endeavourhealth.transform.exceptions.TransformException;
import org.endeavourhealth.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Patient;

public class AdminDomainTransformer {
    public static void transform(FHIRContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001AdminDomain adminDomain = openHR.getAdminDomain();
        if (adminDomain == null)
            return;

        OrganisationTransformer.transform(container, adminDomain);
        LocationTransformer.transform(container, adminDomain);
        HealthcareServiceTransformer.transform(container, adminDomain);
        PractitionerTransformer.transform(container, adminDomain);

        Patient patient = PatientTransformer.transform(adminDomain);
        container.addResource(patient);
    }
}
