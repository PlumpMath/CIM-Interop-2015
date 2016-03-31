package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.FromFhirTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.*;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OpenHRTransformer
{
    public Bundle toFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        FhirContainer container = new FhirContainer();
        container.addResources(AdminDomainTransformer.transform(openHR));
        HealthDomainTransformer.transform(container, openHR);

        Date creationDate = TransformHelper.toDate(openHR.getCreationTime());
        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, openHR.getId(), creationDate, container.getResources());
    }

    public Patient toFhirPatient(String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);
        return PatientTransformer.transform(openHR.getAdminDomain());
    }

    public Bundle toFhirPatientBundle(List<String> openHRXmlList, Boolean includePersons) throws TransformException
    {
        List<Resource> result = new ArrayList<>();

        for (String openHRXml : openHRXmlList)
        {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            if (includePersons)
                result.add(PersonTransformer.transform(openHR.getAdminDomain()));

            result.add(PatientTransformer.transform(openHR.getAdminDomain()));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), result);
    }

	public Organization toFhirOrganisation(String openHROrganisationXml) throws TransformException
    {
		OpenHR001Organisation openHROrganisation = TransformHelper.unmarshall(openHROrganisationXml, OpenHR001Organisation.class);
		return OrganisationTransformer.transform(openHROrganisation);
	}

	public Location toFhirLocation(String openHRLocationXml) throws TransformException
    {
		OpenHR001Location openHR = TransformHelper.unmarshall(openHRLocationXml, OpenHR001Location.class);
		return LocationTransformer.transform(openHR, null);
	}

	public Order toFhirTask(String openHRPatientTaskXml) throws TransformException
    {
		OpenHR001PatientTask openHR = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);
        return TaskTransformer.transform(openHR);
	}

    public Bundle toFhirTaskBundle(List<String> openHRPatientTaskXmlList) throws TransformException
    {
        List<Resource> tasks = new ArrayList<>();

        for (String openHRPatientTaskXml : openHRPatientTaskXmlList)
        {
            OpenHR001PatientTask task = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);
            tasks.add(TaskTransformer.transform(task));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), tasks);
    }

    public String fromFhirTask(Order fhirTask) throws TransformException
    {
		FromFhirTransformer transformer = new FromFhirTransformer();
		OpenHR001OpenHealthRecord openHR = transformer.transformFromTask(fhirTask);

        return TransformHelper.marshall(new ObjectFactory().createOpenHealthRecord(openHR));
	}

    public String fromFhirCondition(Condition fhirCondition) throws TransformException
    {
        FromFhirTransformer transformer = new FromFhirTransformer();
        OpenHR001OpenHealthRecord openHR = transformer.transformFromCondition(fhirCondition);

        return TransformHelper.marshall(new ObjectFactory().createOpenHealthRecord(openHR));
    }
}
