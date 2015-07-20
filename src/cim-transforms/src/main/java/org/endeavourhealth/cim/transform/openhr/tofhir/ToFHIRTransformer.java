package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.AdminDomainTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

public class ToFHIRTransformer {
    public final static String EMISOPEN_NAMESPACE = "http://www.e-mis.com/emisopen";

    public Bundle transformToBundle(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = transform(openHR);
        return createBundle(openHR, container.getResources());
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

    private Bundle createBundle(OpenHR001OpenHealthRecord openHR, Iterable<Resource> resources) {
        Bundle bundle = new Bundle()
                .setType(Bundle.BundleType.SEARCHSET);
        bundle.setId(openHR.getId());
        bundle.setBase(EMISOPEN_NAMESPACE);
        bundle.setMeta(new Meta()
                .setLastUpdated(TransformHelper.toDate(openHR.getCreationTime())));

        resources.forEach(t -> bundle.addEntry(new Bundle.BundleEntryComponent().setResource(t)));

        return bundle;
    }
}