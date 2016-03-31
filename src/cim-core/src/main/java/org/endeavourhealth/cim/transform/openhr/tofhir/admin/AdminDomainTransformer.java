package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Patient;

public class AdminDomainTransformer {
    public static void transform(FhirContainer container, OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        OpenHR001AdminDomain adminDomain = openHR.getAdminDomain();
        if (adminDomain == null)
            return;

        container.addResources(OrganisationTransformer.transform(adminDomain.getOrganisation()));
        LocationTransformer.transform(container, adminDomain);
        HealthcareServiceTransformer.transform(container, adminDomain);
        PractitionerTransformer.transform(container, adminDomain);

        Patient patient = PatientTransformer.transform(adminDomain);
        container.addResource(patient);
    }
}
