package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

class LocationTransformer {
    public final static String LOCATIONTYPE_EXTENSION_URL = "http://www.e-mis.com/emisopen/extension/LocationType";
    public final static String LOCATIONTYPE_SYSTEM = "http://www.e-mis.com/emisopen/LocationType";

    public static void transform(FHIRContainer container, OpenHR001AdminDomain adminDomain) throws TransformException {
        for (OpenHR001Location source: adminDomain.getLocation()) {
            addLocationToResults(container, createLocation(adminDomain, source));
        }
    }

    private static Location createLocation(OpenHR001AdminDomain adminDomain, OpenHR001Location source) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        ToFHIRHelper.ensureDboNotDelete(source);

        Location target = new Location();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getNotes());
        target.setMode(Location.LocationMode.INSTANCE);
        target.setPartOf(createParentReference(source.getParentLocation()));
        target.setStatus(convertCloseDateToStatus(source.getCloseDate()));
        target.setManagingOrganization(createOrganisationReference(adminDomain.getOrganisation(), source.getId()));

        addTelecoms(source.getContact(), target);
        addAddress(source.getAddress(), target);

        addLocationTypeExtension(source.getLocationType(), target);

        return target;
    }

    private static void addLocationToResults(FHIRContainer container, Location location) throws SourceDocumentInvalidException {
        container.getLocations().put(location.getId(), location);
    }

    private static void addTelecoms(List<DtContact> sourceContacts, Location target) throws TransformFeatureNotSupportedException {
        if (sourceContacts == null)
            return;

        List<ContactPoint> telecoms = ContactPointConverter.convert(sourceContacts);
        if (telecoms != null) {
            telecoms.forEach(target::addTelecom);
        }
    }

    private static void addAddress(DtAddress sourceAddress, Location target) throws TransformFeatureNotSupportedException {
        if (sourceAddress == null)
            return;

        target.setAddress(AddressConverter.convert(sourceAddress));
    }

    private static Reference createParentReference(String parentLocationId) {
        if (StringUtils.isBlank(parentLocationId))
            return null;

        return new Reference()
                .setReference(TransformHelper.createResourceReference(Location.class, parentLocationId));
    }

    private static Location.LocationStatus convertCloseDateToStatus(XMLGregorianCalendar closeDate) {
        return closeDate == null
            ? Location.LocationStatus.ACTIVE
            : Location.LocationStatus.INACTIVE;
    }

    private static Reference createOrganisationReference(List<OpenHR001Organisation> organisations, String locationId) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        if (organisations == null)
            return null;

        OpenHR001Organisation organisationWithLocationMatch = organisations.stream()
                .filter(o -> o.getLocations().stream()
                        .anyMatch(l -> l.getLocation().equals(locationId)))
                .collect(StreamExtension.firstOrNullCollector());

        if (organisationWithLocationMatch == null)
            return null;

        return new Reference()
                .setReference(TransformHelper.createResourceReference(Organization.class, organisationWithLocationMatch.getId()));
    }

    private static void addLocationTypeExtension(OpenHR001LocationType locationType, Location target) {
        if (locationType == null)
            return;

        target.addExtension(new Extension()
                .setUrl(LOCATIONTYPE_EXTENSION_URL)
                .setValue(new CodeableConcept()
                                .addCoding(new Coding()
                                                .setSystem(LOCATIONTYPE_SYSTEM)
                                                .setDisplay(locationType.getDisplayName())
                                )
                )
        );
    }
}
