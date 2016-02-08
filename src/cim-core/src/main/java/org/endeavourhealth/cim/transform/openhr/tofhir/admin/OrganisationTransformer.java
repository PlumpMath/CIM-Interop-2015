package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.apache.commons.lang3.StringUtils;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.ReferenceHelper;
import org.endeavourhealth.cim.transform.common.StreamExtension;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.List;

public class OrganisationTransformer {
	public static void transform(FHIRContainer container, OpenHR001AdminDomain adminDomain) throws TransformException
    {
        for (OpenHR001Organisation source: adminDomain.getOrganisation()) {
            container.addResource(createOrganisation(adminDomain, source));
        }
    }

	public static Organization transform(OpenHR001Organisation source) throws TransformFeatureNotSupportedException, SourceDocumentInvalidException {
		ToFHIRHelper.ensureDboNotDelete(source);
		Organization target = new Organization();
		target.setId(source.getId());
		addIdentifiers(source, target);
		target.setName(source.getName());
		target.setType(convertOrganisationType(source));
		target.setActive(source.getCloseDate() == null);

		return target;
	}

    private static Organization createOrganisation(OpenHR001AdminDomain adminDomain, OpenHR001Organisation source) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        Organization target = transform(source);

        OpenHR001Location mainLocation = getLocation(adminDomain.getLocation(), source.getMainLocation());
        if (mainLocation != null) {
            addTelecoms(mainLocation.getContact(), target);
            addAddress(mainLocation.getAddress(), target);
        }

        target.setPartOf(createParentReference(source.getParentOrganisation()));

        return target;
    }

    private static void addIdentifiers(OpenHR001Organisation source, Organization target) throws SourceDocumentInvalidException {
        // add Identifier for NationalPracticeCode(ODS)
        String odsCode = source.getNationalPracticeCode();
        if (StringUtils.isNotBlank(odsCode)) {
            target.addIdentifier(new Identifier()
                    .setSystem("ODS")
                    .setValue(odsCode));
        }

        // add Identifier for EMIS CDB Number
        Integer cdb = source.getCdb();
        if (cdb != null) {
            target.addIdentifier(new Identifier()
                    .setSystem("urn:fhir.nhs.uk:id/LocalIdentifier")
                    .setValue(cdb.toString()));
        }
    }

    private static CodeableConcept convertOrganisationType(OpenHR001Organisation source) throws SourceDocumentInvalidException {
        DtCode organisationType = source.getOrganisationType();

        if (organisationType == null)
            throw new SourceDocumentInvalidException("Missing OrganisationType from Organisation: " + source.getId());

        return new CodeableConcept()
                .addCoding(new Coding()
                                .setSystem("http://www.e-mis.com/emisopen/OrganisationType")
                                .setCode(organisationType.getCode())
                                .setDisplay(organisationType.getDisplayName())
                );
    }

    private static OpenHR001Location getLocation(List<OpenHR001Location> locations, String locationId) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        if (StringUtils.isBlank(locationId))
            return null;

        if (locations == null)
            throw new SourceDocumentInvalidException("Location not found: " + locationId);

        //if the location exists multiple times, then it will just throw a general exception.
        OpenHR001Location location = locations.stream()
                .filter(u -> u.getId().equals(locationId))
                .collect(StreamExtension.singleOrNullCollector());

        if (location == null)
            throw new SourceDocumentInvalidException("Location not found: " + locationId);

        ToFHIRHelper.ensureDboNotDelete(location);

        return location;
    }

    private static void addTelecoms(List<DtContact> sourceContacts, Organization target) throws TransformFeatureNotSupportedException {
        if (sourceContacts == null)
            return;

        List<ContactPoint> telecoms = ContactPointConverter.convert(sourceContacts);
        if (telecoms != null) {
            telecoms.forEach(target::addTelecom);
        }
    }

    private static void addAddress(DtAddress sourceAddress, Organization target) throws TransformFeatureNotSupportedException {
        if (sourceAddress == null)
            return;

        target.addAddress(AddressConverter.convert(sourceAddress));
    }

    private static Reference createParentReference(String parentOrganisationId) {
        if (StringUtils.isBlank(parentOrganisationId))
            return null;

        return ReferenceHelper.createReference(ResourceType.Organization, parentOrganisationId);
    }
}