package org.endeavourhealth.cim.transform.openhr.tofhir.admin;

import org.endeavourhealth.cim.common.ReferenceHelper;
import org.endeavourhealth.cim.common.StreamExtension;
import org.endeavourhealth.cim.transform.TransformHelper;
import org.endeavourhealth.cim.transform.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.openhr.tofhir.FHIRContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRHelper;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;

class PractitionerTransformer {
    public static void transform(FHIRContainer container, OpenHR001AdminDomain adminDomain) throws TransformException {
        for (OpenHR001UserInRole userInRole: adminDomain.getUserInRole()) {
            Practitioner practitioner = createPractitioner(adminDomain, userInRole);
            addPractitionerToResults(container, practitioner);
        }
    }

    private static Practitioner createPractitioner(OpenHR001AdminDomain adminDomain, OpenHR001UserInRole userInRole) throws SourceDocumentInvalidException, TransformFeatureNotSupportedException {
        OpenHR001User user = getUser(adminDomain.getUser(), userInRole.getUser());
        OpenHR001Role role = getRole(adminDomain.getRole(), userInRole.getRole());
        OpenHR001Person person = getPerson(adminDomain.getPerson(), user.getUserPerson());

        ToFHIRHelper.ensureDboNotDelete(userInRole);
        ToFHIRHelper.ensureDboNotDelete(user);
        ToFHIRHelper.ensureDboNotDelete(role);
        ToFHIRHelper.ensureDboNotDelete(person);

        Practitioner practitioner = new Practitioner();
        practitioner.setId(userInRole.getId());

        List<Identifier> identifiers = convertIdentifiers(userInRole.getUserIdentifier());
        if (identifiers != null) {
            identifiers.forEach(practitioner::addIdentifier);
        }

        practitioner.setName(NameConverter.convert(
                person.getForenames(),
                person.getSurname(),
                person.getTitle()));

        List<ContactPoint> telecoms = ContactPointConverter.convert(person.getContact());
        if (telecoms != null) {
            telecoms.forEach(practitioner::addTelecom);
        }

        Practitioner.PractitionerPractitionerRoleComponent practitionerRole =  practitioner.addPractitionerRole();
        practitionerRole.setManagingOrganization(ReferenceHelper.createReference(ResourceType.Organization, role.getOrganisation()));

        DtCode userCategory = role.getUserCategory();

        practitionerRole.setRole(new CodeableConcept()
                .setText(role.getName())
                .addCoding(new Coding()
                                .setSystem("Role")
                                .setCode(userCategory.getCode())
                                .setDisplay(userCategory.getDisplayName())
                ));

        return practitioner;
    }

    private static void addPractitionerToResults(FHIRContainer container, Practitioner practitioner) throws SourceDocumentInvalidException {
        container.getAdminResources().put(practitioner.getId(), practitioner);
    }


    private static OpenHR001User getUser(List<OpenHR001User> userList, String userId) throws SourceDocumentInvalidException {
        if (userList == null)
            throw new SourceDocumentInvalidException("User not found: " + userId);

        OpenHR001User user = userList.stream()
                .filter(u -> u.getId().equals(userId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the user is there multiple times, then it will just throw a general exception.

        if (user == null)
            throw new SourceDocumentInvalidException("User not found: " + userId);

        return user;
    }

    private static OpenHR001Role getRole(List<OpenHR001Role> roleList, String roleId) throws SourceDocumentInvalidException {
        if (roleList == null)
            throw new SourceDocumentInvalidException("Role not found: " + roleId);

        // the following should be a singleOrNullCollector as a role with the same id should only occur once however OpenHR appears to duplicate roles
        OpenHR001Role role = roleList.stream()
                .filter(u -> u.getId().equals(roleId))
                .collect(StreamExtension.firstOrNullCollector());

        if (role == null)
            throw new SourceDocumentInvalidException("Role not found: " + roleId);

        return role;
    }

    private static OpenHR001Person getPerson(List<OpenHR001Person> sourcePeople, String personId) throws TransformFeatureNotSupportedException, SourceDocumentInvalidException {
        if (sourcePeople == null)
            throw new TransformFeatureNotSupportedException("No AdminDomain.Person found.");

        OpenHR001Person person = sourcePeople.stream()
                .filter(p -> p.getId().equals(personId))
                .collect(StreamExtension.singleOrNullCollector());

        //if the person is there multiple times, then it will just throw a general exception.

        if (person == null)
            throw new SourceDocumentInvalidException("Person not found: " + personId);

        return person;
    }

    private static List<Identifier> convertIdentifiers(List<DtUserIdentifier> sourceIdentifiers) throws TransformFeatureNotSupportedException {
        if (sourceIdentifiers == null || sourceIdentifiers.isEmpty())
            return null;

        List<Identifier> targetIdentifiers = new ArrayList<>();

        for (DtUserIdentifier source: sourceIdentifiers) {
            targetIdentifiers.add(new Identifier()
                .setSystem(convertIdentifierType(source.getIdentifierType()))
                .setValue(source.getValue()));
        }

        return targetIdentifiers;
    }

    private static String convertIdentifierType(VocUserIdentifierType openHRType) throws TransformFeatureNotSupportedException {
        switch (openHRType)
        {
            case GP:
                return "GmcCode";
            case NAT:
                return "NationalCode";
            case PRES:
                return "PrescriptionCode";
            default:
                throw new TransformFeatureNotSupportedException("VocUserIdentifierType not supported: " + openHRType.toString());
        }
    }
}