package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.common.FhirUris;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.OpenHRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.hl7.fhir.instance.model.*;

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
        target.setMeta(new Meta().addProfile(FhirUris.PROFILE_URI_LOCATION));

		target.setName(source.getName());

        if (!StringUtils.isBlank(source.getParentLocation()))
            target.setPartOf(ReferenceHelper.createReference(ResourceType.Location, source.getParentLocation()));

		target.setStatus(source.getCloseDate() == null ? Location.LocationStatus.ACTIVE : Location.LocationStatus.INACTIVE);

        if ((source.getOpenDate() != null) || (source.getCloseDate() != null))
        {
            Period period = new Period();

            if (source.getOpenDate() != null)
                period.setStart(TransformHelper.toDate(source.getOpenDate()));

            if (source.getCloseDate() != null)
                period.setEnd(TransformHelper.toDate(source.getCloseDate()));

            target.getStatusElement().addExtension(TransformHelper.createSimpleExtension(FhirUris.EXTENSION_URI_ACTIVEPERIOD, period));
        }

        if (source.getAddress() != null)
            target.setAddress(AddressConverter.convert(source.getAddress()));

        if (source.getLocationType() != null)
            if (!StringUtils.isBlank(source.getLocationType().getDisplayName()))
                target.setType(new CodeableConcept().setText(source.getLocationType().getDisplayName()));

        if (adminDomain != null)
        {
            Reference reference = createOrganisationReference(adminDomain.getOrganisation(), source.getId());

            if (reference != null)
                target.setManagingOrganization(reference);
        }

        if (source.getContact() != null)
            for (ContactPoint telecom : ContactPointConverter.convert(source.getContact()))
                target.addTelecom(telecom);

        return target;
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
}
