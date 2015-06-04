package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.AdminDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

public class ToFHIRTransformer {

    public Bundle transformToBundle(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = transform(openHR);
        return createBundleFromResults(openHR, container);
    }

    public Patient transformToPatient(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = transform(openHR);
        return container.getPatient();
    }

    private FHIRContainer transform(OpenHR001OpenHealthRecord openHR) throws TransformException {
        FHIRContainer container = new FHIRContainer();
        AdminDomainTransformer.transform(container, openHR);
        return container;
    }

    private Bundle createBundleFromResults(OpenHR001OpenHealthRecord openHR, FHIRContainer container) {
        Bundle bundle = new Bundle()
                .setType(Bundle.BundleType.SEARCHSET);
        bundle.setId(openHR.getId());
        bundle.setBase("http://www.e-mis.com/emisopen");
        bundle.setMeta(new Meta().setLastUpdated(TransformHelper.toDate(openHR.getCreationTime())));

        for (Organization organisation: container.getOrganisations().values())
            bundle.addEntry(new Bundle.BundleEntryComponent().setResource(organisation));

        for (Practitioner practitioner: container.getPractitioners().values())
            bundle.addEntry(new Bundle.BundleEntryComponent().setResource(practitioner));

        bundle.addEntry(new Bundle.BundleEntryComponent().setResource(container.getPatient()));

        return bundle;
    }
}
