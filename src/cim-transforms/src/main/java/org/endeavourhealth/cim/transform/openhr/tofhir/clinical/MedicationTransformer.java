package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.DtDatePart;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.DateType;
import org.hl7.fhir.instance.model.MedicationPrescription;

import java.util.Date;

class MedicationTransformer implements ClinicalResourceTransformer {
    public MedicationPrescription transform(OpenHR001HealthDomain healthDomain, FHIRContainer container, OpenHR001HealthDomain.Event source) throws TransformException {
        MedicationPrescription target = new MedicationPrescription();

        target.setId(source.getId());
        target.setDateWritten(convertEffectiveDateTime(source.getEffectiveTime()));

        return target;
    }

    private Date convertEffectiveDateTime(DtDatePart source) throws TransformException {
        if (source == null)
            throw new SourceDocumentInvalidException("Invalid DateTime");

        return TransformHelper.toDate(source.getValue());
    }
}
