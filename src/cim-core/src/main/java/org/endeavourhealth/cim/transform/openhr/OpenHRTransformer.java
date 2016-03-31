package org.endeavourhealth.cim.transform.openhr;

import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.common.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.openhr.fromfhir.FromFHIRTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.*;
import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.hl7.fhir.instance.model.*;

import javax.xml.bind.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenHRTransformer {

    public Bundle toFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException
    {
        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        ToFHIRTransformer transformer = new ToFHIRTransformer();
        return transformer.transformToBundle(bundleProperties, openHR);
    }

    public Patient toFhirPatient(String openHRXml) throws TransformException {

        OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

        ToFHIRTransformer transformer = new ToFHIRTransformer();
        return transformer.transformToPatient(openHR);
    }

    public Bundle toFhirPersonBundle(List<String> openHRXmlArray) throws TransformException {

        List<Resource> result = new ArrayList<>();

        for (String openHRXml : openHRXmlArray) {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            ToFHIRTransformer transformer = new ToFHIRTransformer();
            result.add(transformer.transformToPerson(openHR));
            result.add(transformer.transformToPatient(openHR));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), result);
    }

    public Bundle toFhirPatientBundle(List<String> openHRXmlArray) throws TransformException {

        List<Resource> result = new ArrayList<>();

        for (String openHRXml : openHRXmlArray) {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            ToFHIRTransformer transformer = new ToFHIRTransformer();
            result.add(transformer.transformToPatient(openHR));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), result);
    }

    public String fromFhirCondition(Condition condition) throws TransformException {

        FromFHIRTransformer transformer = new FromFHIRTransformer();
        OpenHR001OpenHealthRecord openHR = transformer.transformFromCondition(condition);
        return serializeOpenHR(openHR);
    }

	public Organization toFhirOrganisation(String openHRXml) throws TransformException {
		OpenHR001Organisation openHR = TransformHelper.unmarshall(openHRXml, OpenHR001Organisation.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToOrganisation(openHR);
	}

	public Location toFhirLocation(String openHRXml) throws TransformException {
		OpenHR001Location openHR = TransformHelper.unmarshall(openHRXml, OpenHR001Location.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToLocation(openHR);
	}

	public Order toFhirTask(String openHRXml) throws TransformException {
		OpenHR001PatientTask openHR = TransformHelper.unmarshall(openHRXml, OpenHR001PatientTask.class);

		ToFHIRTransformer transformer = new ToFHIRTransformer();
		return transformer.transformToTask(openHR);
	}

	public String fromFhirTask(Order task) throws TransformException {
		FromFHIRTransformer transformer = new FromFHIRTransformer();
		OpenHR001OpenHealthRecord openHR = transformer.transformFromTask(task);
		return serializeOpenHR(openHR);
	}

	public Bundle toFhirTaskBundle(List<String> openHRXmlArray) throws TransformException {
		List<Resource> tasks = new ArrayList<>();

		for (String openHRXml : openHRXmlArray) {
			OpenHR001PatientTask task = TransformHelper.unmarshall(openHRXml, OpenHR001PatientTask.class);

			ToFHIRTransformer transformer = new ToFHIRTransformer();
			tasks.add(transformer.transformToTask(task));
		}

		return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), tasks);
	}

    private String serializeOpenHR(OpenHR001OpenHealthRecord openHR) throws TransformException {
        try {
            ObjectFactory factory = new ObjectFactory();
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(OpenHR001OpenHealthRecord.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.marshal(factory.createOpenHealthRecord(openHR), writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new SerializationException("Error deserialising OpenHR", e);
        }
    }
}
