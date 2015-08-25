package org.endeavourhealth.cim.transform.openhr;

import org.endeavourhealth.cim.transform.common.BundleHelper;
import org.endeavourhealth.cim.transform.common.BundleProperties;
import org.endeavourhealth.cim.transform.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.TransformHelper;
import org.endeavourhealth.cim.transform.openhr.fromfhir.FromFHIRTransformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.ObjectFactory;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.*;

import javax.xml.bind.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenHRTransformer {

    public Bundle toFhirBundle(BundleProperties bundleProperties, String openHRXml) throws TransformException {

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

        List<Resource> persons = new ArrayList<>();

        for (String openHRXml : openHRXmlArray) {
            OpenHR001OpenHealthRecord openHR = TransformHelper.unmarshall(openHRXml, OpenHR001OpenHealthRecord.class);

            ToFHIRTransformer transformer = new ToFHIRTransformer();
            persons.add(transformer.transformToPerson(openHR));
        }

        return BundleHelper.createBundle(Bundle.BundleType.SEARCHSET, UUID.randomUUID().toString(), "", persons);
    }

    public String fromFHIRCondition(Condition condition) throws TransformException {

        FromFHIRTransformer transformer = new FromFHIRTransformer();
        OpenHR001OpenHealthRecord openHR = transformer.transformFromCondition(condition);
        return serializeOpenHR(openHR);
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
