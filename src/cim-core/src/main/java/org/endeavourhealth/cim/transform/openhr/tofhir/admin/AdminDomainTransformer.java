package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class AdminDomainTransformer
{
    public static List<Resource> transform(OpenHR001OpenHealthRecord openHR) throws TransformException
    {
        ArrayList<Resource> resources = new ArrayList<>();

        OpenHR001AdminDomain adminDomain = openHR.getAdminDomain();

        if (adminDomain != null)
        {
            resources.addAll(OrganisationTransformer.transform(adminDomain.getOrganisation()));
            resources.addAll(LocationTransformer.transform(adminDomain));
            resources.addAll(PractitionerTransformer.transform(adminDomain));
            resources.add(PatientTransformer.transform(adminDomain));
        }

        return resources;
    }
}
