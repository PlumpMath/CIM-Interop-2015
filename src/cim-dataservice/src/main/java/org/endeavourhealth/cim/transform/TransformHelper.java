package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringReader;
import java.util.Date;
import java.util.GregorianCalendar;

public class TransformHelper {
    public static Date toDate(XMLGregorianCalendar value) {
        if (value == null) {
            return null;
        }
        return value.toGregorianCalendar().getTime();
    }

    public static XMLGregorianCalendar fromDate(Date value) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date(System.currentTimeMillis()));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }


    public static <T extends Resource> String createResourceReference(Class<T> resourceClass, String id) {
        return resourceClass.getSimpleName() + "/" + id;
    }

    public static <T> T unmarshall(String xml, Class<T> objectClass) throws SerializationException
    {
        try
        {
            StringReader reader = new StringReader(xml);
            JAXBContext jaxbContent = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = jaxbContent.createUnmarshaller();
            return (T)unmarshaller.unmarshal(reader);
        }
        catch (JAXBException e)
        {
            throw new SerializationException(String.format("Error deserialising %s", objectClass.getTypeName()), e);
        }
    }
}
