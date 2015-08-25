package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.AdminDomainTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

import java.util.Date;

public class ToFHIRTransformer {

    public Bundle transformToBundle(BundleProperties bundleProperties, OpenHR001OpenHealthRecord openHR) throws TransformException {

        FHIRContainer container = transform(openHR);
        Date creationDate = TransformHelper.toDate(openHR.getCreationTime());
        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, openHR.getId(), bundleProperties.getBaseUri(), creationDate, container.getResources());
    }

    public Patient transformToPatient(OpenHR001OpenHealthRecord openHRXml) throws TransformException {

        FHIRContainer container = transform(openHRXml);
        return container.getPatientResource();
    }

    private FHIRContainer transform(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = new FHIRContainer();

        AdminDomainTransformer.transform(container, openHR);
        HealthDomainTransformer.transform(container, openHR);

        return container;
    }
}