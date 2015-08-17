package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.common.BundleHelper;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.AdminDomainTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

import java.util.Date;

public class ToFHIRTransformer {

    public Bundle transformToBundle(String baseUri, OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = transform(openHR);
        Date creationDate = TransformHelper.toDate(openHR.getCreationTime());
        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, openHR.getId(), baseUri, creationDate, container.getResources());
    }

    public Patient transformToPatient(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = transform(openHR);
        return container.getPatientResource();
    }

    private FHIRContainer transform(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = new FHIRContainer();

        AdminDomainTransformer.transform(container, openHR);
        HealthDomainTransformer.transform(container, openHR);

        return container;
    }
}