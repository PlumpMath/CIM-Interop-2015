package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.FromFHIRTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.hl7.fhir.instance.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenHRTransformer
{
    public Bundle toFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        ToFHIRTransformer transformer = new ToFHIRTransformer();
        return transformer.transformToBundle(bundleProperties, openHR);
    }

    public Patient toFhirPatient(String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        ToFHIRTransformer transformer = new ToFHIRTransformer();
        return transformer.transformToPatient(openHR);
    }

    public Bundle toFhirPatientBundle(List<String> openHRXmlList, Boolean includePersons) throws TransformException
    {
        List<Resource> result = new ArrayList<>();

        for (String openHRXml : openHRXmlList)
        {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            ToFHIRTransformer transformer = new ToFHIRTransformer();

            if (includePersons)
                result.add(transformer.transformToPerson(openHR));

            result.add(transformer.transformToPatient(openHR));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), result);
    }

	public Organization toFhirOrganisation(String openHROrganisationXml) throws TransformException
    {
		OpenHR001Organisation openHR = TransformHelper.unmarshall(openHROrganisationXml, OpenHR001Organisation.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToOrganisation(openHR);
	}

	public Location toFhirLocation(String openHRLocationXml) throws TransformException
    {
		OpenHR001Location openHR = TransformHelper.unmarshall(openHRLocationXml, OpenHR001Location.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToLocation(openHR);
	}

	public Order toFhirTask(String openHRPatientTaskXml) throws TransformException
    {
		OpenHR001PatientTask openHR = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToTask(openHR);
	}

    public Bundle toFhirTaskBundle(List<String> openHRPatientTaskXmlList) throws TransformException
    {
        List<Resource> tasks = new ArrayList<>();

        for (String openHRPatientTaskXml : openHRPatientTaskXmlList)
        {
            OpenHR001PatientTask task = TransformHelper.unmarshall(openHRPatientTaskXml, OpenHR001PatientTask.class);

            ToFHIRTransformer transformer = new ToFHIRTransformer();
            tasks.add(transformer.transformToTask(task));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), tasks);
    }

    public String fromFhirTask(Order fhirTask) throws TransformException
    {
		FromFHIRTransformer transformer = new FromFHIRTransformer();
		OpenHR001OpenHealthRecord openHR = transformer.transformFromTask(fhirTask);

        return TransformHelper.marshall(new ObjectFactory().createOpenHealthRecord(openHR));
	}

    public String fromFhirCondition(Condition fhirCondition) throws TransformException
    {
        FromFHIRTransformer transformer = new FromFHIRTransformer();
        OpenHR001OpenHealthRecord openHR = transformer.transformFromCondition(fhirCondition);

        return TransformHelper.marshall(new ObjectFactory().createOpenHealthRecord(openHR));
    }
}
