package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.Results;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.List;

class OrganisationTransformer {
    public static void transform(Results results, OpenHR001AdminDomain adminDomain) throws TransformException {
        for (OpenHR001Organisation source: adminDomain.getOrganisation()) {
            addOrganisationToResults(results, createOrganisation(adminDomain, source));
        }
    }

    private static Organization createOrganisation(OpenHR001AdminDomain adminDomain, OpenHR001Organisation source) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        OpenHR001Location mainLocation = getLocation(adminDomain.getLocation(), source.getMainLocation());

        ToFHIRHelper.ensureDboNotDelete(source);
        if (mainLocation != null)
            ToFHIRHelper.ensureDboNotDelete(mainLocation);

        Organization target = new Organization();
        target.setId(source.getId());

        String odsCode = source.getNationalPracticeCode();
        if (StringUtils.isNotBlank(odsCode)) {
            target.addIdentifier(new Identifier()
                    .setSystem("ODS")
                    .setValue(odsCode));
        }

        target.setName(source.getName());

        DtCode organisationType = source.getOrganisationType();
        if (organisationType != null) {
            target.setType(new CodeableConcept()
                    .addCoding(new Coding()
                                    .setSystem("http://www.e-mis.com/emisopen/OrganisationType")
                                    .setCode(organisationType.getCode())
                                    .setDisplay(organisationType.getDisplayName())
                    ));
        }

        if (mainLocation != null) {
            List<ContactPoint> telecoms = ContactPointConverter.convert(mainLocation.getContact());
            if (telecoms != null) {
                telecoms.forEach(target::addTelecom);
            }

            if (mainLocation.getAddress() != null)
                target.addAddress(AddressConverter.convert(mainLocation.getAddress()));
        }

        String parentOrganisationId = source.getParentOrganisation();
        if (StringUtils.isNotBlank(parentOrganisationId)) {
            target.setPartOf(new Reference().setReference(TransformHelper.createResourceReference(Organization.class, parentOrganisationId)));
        }

        if (source.getCloseDate() == null)
            target.setActive(true);
        else
            target.setActive(false);

        return target;
    }

    private static void addOrganisationToResults(Results results, Organization organisation) throws SourceDocumentInvalidException {
        results.getOrganisations().put(organisation.getId(), organisation);
    }

    private static OpenHR001Location getLocation(List<OpenHR001Location> locationList, String locationId) throws SourceDocumentInvalidException {
        if (StringUtils.isBlank(locationId))
            return null;

        if (locationList == null)
            throw new SourceDocumentInvalidException("Location not found: " + locationId);

        OpenHR001Location location = locationList.stream()
                .filter(u -> u.getId().equals(locationId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the location is there multiple times, then it will just throw a general exception.

        if (location == null)
            throw new SourceDocumentInvalidException("Location not found: " + locationId);

        return location;
    }
}
