package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;

import java.util.List;

class HealthcareServiceTransformer {
    public static void transform(FHIRContainer container, OpenHR001AdminDomain adminDomain) throws TransformException {

    }

    private static OpenHR001Organisation getPatientBasedOrganisation(OpenHR001AdminDomain adminDomain) throws TransformFeatureNotSupportedException {
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

    private static OpenHR001Patient getPatient(OpenHR001AdminDomain adminDomain) throws TransformFeatureNotSupportedException {

        List<OpenHR001Patient> patients = adminDomain.getPatient();

        if (patients == null || patients.isEmpty())
            throw new TransformFeatureNotSupportedException("No AdminDomain.Patients found.");

        if (patients.size() != 1)
            throw new TransformFeatureNotSupportedException("Only single patient supported in AdminDomain.Patients.");

        return patients.get(0);
    }

}