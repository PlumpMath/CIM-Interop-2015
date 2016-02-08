package org.endeavourhealth.cim.transform.emisopen;

import org.endeavourhealth.cim.transform.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomuserdetails.PersonType;
import org.endeavourhealth.cim.transform.schemas.emisopen.eomuserdetails.UserDetails;
import org.endeavourhealth.cim.transform.exceptions.TransformFeatureNotSupportedException;
import org.hl7.fhir.instance.model.*;

public class UserTransformer {

    public static Person transformToPersonResource(UserDetails userDetails) throws SerializationException, TransformFeatureNotSupportedException {
		Person fhirPerson = new Person();
		PersonType eopenPerson = userDetails.getPerson();

		HumanName humanName = new HumanName();
		humanName.addFamily(eopenPerson.getLastName());
		humanName.addGiven(eopenPerson.getFirstNames());
		humanName.addPrefix(eopenPerson.getTitle());

		fhirPerson.addName(humanName);

		Identifier Guid = new Identifier();
		Guid.setIdElement(new IdType(eopenPerson.getGUID().toString()));
		fhirPerson.addIdentifier(Guid);

		return fhirPerson;
    }
}
