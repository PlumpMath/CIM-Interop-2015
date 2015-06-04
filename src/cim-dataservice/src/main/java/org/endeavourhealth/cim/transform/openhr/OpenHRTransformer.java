package org.endeavourhealth.cim.transform.openhr;

import org.endeavourhealth.cim.transform.SerializationException;
import org.endeavourhealth.cim.transform.TransformException;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.openhr.tofhir.ToFHIRTransformer;
import org.endeavourhealth.cim.transform.schemas.openhr.ObjectFactory;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Condition;
import org.hl7.fhir.instance.model.Patient;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class OpenHRTransformer implements Transformer {
    public Bundle toFHIRBundle(String sourceData) throws TransformException {
        OpenHR001OpenHealthRecord openHR = deserializeOpenHR(sourceData);

        ToFHIRTransformer transformer = new ToFHIRTransformer();

        return transformer.transformToBundle(openHR);
    }

    public Patient toFHIRPatient(String sourceData) throws TransformException {
        OpenHR001OpenHealthRecord openHR = deserializeOpenHR(sourceData);

        ToFHIRTransformer transformer = new ToFHIRTransformer();

        return transformer.transformToPatient(openHR);
    }

    public String fromFHIRCondition(Condition condition) throws TransformException {
        return null;
    }

    private OpenHR001OpenHealthRecord deserializeOpenHR(String openHRAsString) throws TransformException {
        try {
            StringReader reader = new StringReader(openHRAsString);
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<OpenHR001OpenHealthRecord> jaxbElement = (JAXBElement<OpenHR001OpenHealthRecord>)jaxbUnmarshaller.unmarshal(reader);
            return jaxbElement.getValue();
        } catch (JAXBException e) {
            throw new SerializationException("Error deserialising OpenHR", e);
        }
    }

}
