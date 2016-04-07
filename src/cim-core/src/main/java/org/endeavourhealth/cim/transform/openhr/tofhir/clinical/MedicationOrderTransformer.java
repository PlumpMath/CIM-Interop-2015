package org.endeavourhealth.cim.transform.openhr.tofhir.clinical;

import org.endeavourhealth.cim.transform.common.CodeHelper;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.common.EventEncounterMap;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001HealthDomain;
import org.hl7.fhir.instance.model.Flag;
import org.hl7.fhir.instance.model.MedicationOrder;
import org.hl7.fhir.instance.model.Meta;
import org.hl7.fhir.instance.model.ResourceType;

public class MedicationOrderTransformer implements ClinicalResourceTransformer
{
    public MedicationOrder transform(OpenHR001HealthDomain.Event source, OpenHR001HealthDomain healthDomain, EventEncounterMap eventEncounterMap) throws TransformException
    {
        MedicationOrder target = new MedicationOrder();

        target.setId(source.getId());
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_MEDICATION_ORDER));

        target.setPatient(ReferenceHelper.createReference(ResourceType.Patient, source.getPatient()));

        target.setMedication(CodeHelper.convertCode(source.getCode()));

        return target;
    }
}