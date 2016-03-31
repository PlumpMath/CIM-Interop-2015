package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001CaseloadPatient;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001AdminDomain;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Organisation;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001Patient;
import org.hl7.fhir.instance.model.HealthcareService;

import java.util.ArrayList;
import java.util.List;

class HealthcareServiceTransformer
{
    public static List<HealthcareService> transform(OpenHR001AdminDomain adminDomain) throws TransformException
    {
        return new ArrayList<HealthcareService>();
    }

    private static OpenHR001Organisation getPatientBasedOrganisation(OpenHR001AdminDomain adminDomain) throws TransformException
    {
        OpenHR001Patient patient = getPatient(adminDomain);

        List<OpenHR001CaseloadPatient> caseloads = patient.getCaseloadPatient();

        if (caseloads == null || caseloads.isEmpty())
            throw new TransformFeatureNotSupportedException("No CaseloadPatient found for patient: " + patient.getId());

        if (caseloads.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single CaseloadPatient supported for patient: " + patient.getId());

        OpenHR001CaseloadPatient caseload = caseloads.get(0);

        OpenHR001Organisation organisation = adminDomain.getOrganisation().stream()
                .filter(o -> o.getId().equals(caseload.getOrganisation()))
                .collect(StreamExtension.singleCollector());

        return organisation;
    }

    private static OpenHR001Patient getPatient(OpenHR001AdminDomain adminDomain) throws TransformException
    {
        List<OpenHR001Patient> patients = adminDomain.getPatient();

        if (patients == null || patients.isEmpty())
            throw new TransformFeatureNotSupportedException("No AdminDomain.Patients found.");

        if (patients.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single patient supported in AdminDomain.Patients.");

        return patients.get(0);
    }
}
