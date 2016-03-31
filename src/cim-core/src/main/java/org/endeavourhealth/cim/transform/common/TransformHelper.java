package org.endeavourhealth.cim.transform.common;

import org.endeavourhealth.cim.transform.common.exceptions.SerializationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
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
        gregorianCalendar.setTime(value);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static <T> T unmarshall(String xml, Class<T> objectClass) throws SerializationException
    {
        try  {
            StringReader reader = new StringReader(xml);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

            JAXBContext jaxbContent = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = jaxbContent.createUnmarshaller();

            return (T)unmarshaller.unmarshal(xmlReader, objectClass).getValue();
        } catch (Exception e) {
            throw new SerializationException(String.format("Error deserialising %s", objectClass.getTypeName()), e);
        }
    }
}
