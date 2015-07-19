package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.MedicationPrescription;

class MedicationTransformer implements ClinicalResourceTransformer {
    public MedicationPrescription transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        MedicationPrescription target = new MedicationPrescription();
        target.setId(source.getId());

        return target;
    }
}
