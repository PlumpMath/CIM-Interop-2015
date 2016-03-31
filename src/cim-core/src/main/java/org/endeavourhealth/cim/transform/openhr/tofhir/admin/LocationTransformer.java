package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.tofhir.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.hl7.fhir.instance.model.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class LocationTransformer
{
    public static List<Location> transform(OpenHR001AdminDomain adminDomain) throws TransformException
    {
        ArrayList<Location> locations = new ArrayList<>();

        for (OpenHR001Location source: adminDomain.getLocation())
            locations.add(transform(source, adminDomain));

        return locations;
    }

	public static Location transform(OpenHR001Location source, OpenHR001AdminDomain adminDomain) throws TransformException
    {
		OpenHRHelper.ensureDboNotDelete(source);

		Location target = new Location();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setDescription(source.getNotes());
		target.setMode(Location.LocationMode.INSTANCE);
		target.setPartOf(createParentReference(source.getParentLocation()));
		target.setStatus(convertCloseDateToStatus(source.getCloseDate()));

		addTelecoms(source.getContact(), target);
		addAddress(source.getAddress(), target);

		addLocationTypeExtension(source.getLocationType(), target);

        if (adminDomain != null)
            target.setManagingOrganization(createOrganisationReference(adminDomain.getOrganisation(), source.getId()));

		return target;
	}

    private static void addTelecoms(List<DtContact> sourceContacts, Location target) throws TransformException
    {
        if (sourceContacts == null)
            return;

        List<ContactPoint> telecoms = ContactPointConverter.convert(sourceContacts);
        if (telecoms != null) {
            telecoms.forEach(target::addTelecom);
        }
    }

    private static void addAddress(DtAddress sourceAddress, Location target) throws TransformException
    {
        if (sourceAddress == null)
            return;

        target.setAddress(AddressConverter.convert(sourceAddress));
    }

    private static Reference createParentReference(String parentLocationId)
    {
        if (StringUtils.isBlank(parentLocationId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Location, parentLocationId);
    }

    private static Location.LocationStatus convertCloseDateToStatus(XMLGregorianCalendar closeDate)
    {
        return closeDate == null
            ? Location.LocationStatus.ACTIVE
            : Location.LocationStatus.INACTIVE;
    }

    private static Reference createOrganisationReference(List<OpenHR001Organisation> organisations, String locationId) throws TransformException
    {
        if (organisations == null)
            return null;

        OpenHR001Organisation organisationWithLocationMatch = organisations.stream()
                .filter(o -> o.getLocations().stream()
                        .anyMatch(l -> l.getLocation().equals(locationId)))
                .collect(StreamExtension.firstOrNullCollector());

        if (organisationWithLocationMatch == null)
            return null;

        return ReferenceHelper.createReference(ResourceType.Organization, organisationWithLocationMatch.getId());
    }

    private static void addLocationTypeExtension(OpenHR001LocationType locationType, Location target)
    {
        if (locationType == null)
            return;

        target.addExtension(new Extension()
                .setUrl(FhirUris.LOCATIONTYPE_EXTENSION_URL)
                .setValue(new CodeableConcept()
                                .addCoding(new Coding()
                                                .setSystem(FhirUris.LOCATIONTYPE_SYSTEM)
                                                .setDisplay(locationType.getDisplayName())
                                )
                )
        );
    }
}
