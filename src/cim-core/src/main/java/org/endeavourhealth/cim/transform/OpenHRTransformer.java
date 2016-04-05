package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.FromFhirTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.FhirContainer;
import org.endeavourhealth.cim.transform.openhr.tofhir.admin.*;
import org.endeavourhealth.cim.transform.openhr.tofhir.clinical.HealthDomainTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;

public class OpenHRTransformer
{
    public List<Resource> toFhirFullRecord(String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        FhirContainer container = new FhirContainer();

        if (openHR.getAdminDomain() != null)
        {
            container.addResources(OrganisationTransformer.transform(openHR.getAdminDomain().getOrganisation()));
            container.addResources(LocationTransformer.transform(openHR.getAdminDomain()));
            container.addResources(PractitionerTransformer.transform(openHR.getAdminDomain()));
            container.addResource(PatientTransformer.transform(openHR.getAdminDomain()));
        }

        if (openHR.getHealthDomain() != null)
        {
            HealthDomainTransformer.transform(container, openHR);
        }

        return container.getResources();
    }

    public Patient toFhirPatient(String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);
        return PatientTransformer.transform(openHR.getAdminDomain());
    }

    public List<Resource> toFhirPatients(List<String> openHRXmlList, Boolean includePersons) throws TransformException
    {
        List<Resource> result = new ArrayList<>();

        for (String openHRXml : openHRXmlList)
        {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            if (includePersons)
                result.add(PersonTransformer.transform(openHR.getAdminDomain()));

            result.add(PatientTransformer.transform(openHR.getAdminDomain()));
        }

        return result;
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

    public List<Practitioner> toFhirPractitioners(String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);
        return PractitionerTransformer.transform(openHR.getAdminDomain());
    }

	public Order toFhirTask(String openHRPatientTaskXml) throws TransformException
    {
		OpenHR001PatientTask openHR = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);
        return TaskTransformer.transform(openHR);
	}

    public List<Order> toFhirTasks(List<String> openHRPatientTaskXmlList) throws TransformException
    {
        List<Order> tasks = new ArrayList<>();

        for (String openHRPatientTaskXml : openHRPatientTaskXmlList)
        {
            OpenHR001PatientTask task = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);
            tasks.add(TaskTransformer.transform(task));
        }

        return tasks;
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
