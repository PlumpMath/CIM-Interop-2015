package org.endeavourhealth.cim.transform.openhr.tofhir;

import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.AdminDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

public class ToFHIRTransformer {

    public Bundle transformToBundle(OpenHR001OpenHealthRecord openHR) throws TransformException {
        Results results = transform(openHR);
        return createBundleFromResults(openHR, results);
    }

    public Patient transformToPatient(OpenHR001OpenHealthRecord openHR) throws TransformException {
        Results results = transform(openHR);
        return results.getPatient();
    }

    private Results transform(OpenHR001OpenHealthRecord openHR) throws TransformException {
        Results results = new Results();
        AdminDomainTransformer.transform(results, openHR);
        return results;
    }

    private Bundle createBundleFromResults(OpenHR001OpenHealthRecord openHR, Results results) {
        Bundle bundle = new Bundle()
                .setType(Bundle.BundleType.SEARCHSET);
        bundle.setId(openHR.getId());
        bundle.setBase("http://www.e-mis.com/emisopen");
        bundle.setMeta(new Meta().setLastUpdated(TransformHelper.toDate(openHR.getCreationTime())));

        for (Organization organisation: results.getOrganisations().values())
            bundle.addEntry(new Bundle.BundleEntryComponent().setResource(organisation));

        for (Practitioner practitioner: results.getPractitioners().values())
            bundle.addEntry(new Bundle.BundleEntryComponent().setResource(practitioner));

        bundle.addEntry(new Bundle.BundleEntryComponent().setResource(results.getPatient()));

        return bundle;
    }
}
