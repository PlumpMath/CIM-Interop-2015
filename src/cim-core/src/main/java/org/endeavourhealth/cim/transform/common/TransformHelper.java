package org.endeavourhealth.cim.transform.common;

import org.endeavourhealth.cim.transform.common.exceptions.SerializationException;
import org.endeavourhealth.cim.transform.common.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformException;
import org.endeavourhealth.cim.transform.common.exceptions.TransformFeatureNotSupportedException;
import org.endeavourhealth.cim.transform.schemas.openhr.ObjectFactory;
import org.endeavourhealth.cim.transform.schemas.openhr.OpenHR001OpenHealthRecord;
import org.hl7.fhir.instance.model.Extension;
import org.hl7.fhir.instance.model.Identifier;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TransformHelper
{
    public static UUID parseUUID(String id) throws TransformException
    {
        try
        {
            return UUID.fromString(id);
        }
        catch (Exception e)
        {
            throw new SourceDocumentInvalidException("Could not parse UUID: " + id, e);
        }
    }

    public static XMLGregorianCalendar toCalendar(Date date) throws TransformException
    {
        try
        {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        }
        catch (Exception e)
        {
            throw new TransformFeatureNotSupportedException(e);
        }
    }

    public static Date toDate(XMLGregorianCalendar value)
    {
        if (value == null)
            return null;

        return value.toGregorianCalendar().getTime();
    }

    public static Identifier createIdentifier(String system, String value)
    {
        return new Identifier().setSystem(system).setValue(value);
    }

    public static Extension createSimpleExtension(String uri, org.hl7.fhir.instance.model.Type value)
    {
        return new Extension().setUrl(uri).setValue(value);
    }

    public static XMLGregorianCalendar fromDate(Date value) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(value);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static <T> String marshall(JAXBElement<T> object) throws SerializationException
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller m = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            m.marshal(object, stringWriter);
            return stringWriter.toString();
        }
        catch (JAXBException e)
        {
            throw new SerializationException(String.format("Error serialising %s", object.getClass().getTypeName()), e);
        }
    }

    public static <T> T unmarshall(String xml, Class<T> objectClass) throws SerializationException
    {
        try
        {
            StringReader reader = new StringReader(xml);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

            JAXBContext jaxbContent = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = jaxbContent.createUnmarshaller();

            return (T)unmarshaller.unmarshal(xmlReader, objectClass).getValue();
        }
        catch (Exception e)
        {
            throw new SerializationException(String.format("Error deserialising %s", objectClass.getTypeName()), e);
        }
    }
}
