package org.endeavourhealth.core.serializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class XmlSerializer {

    public static String serialize(JAXBElement entity) throws SerializationException {

        try (StringWriter sw = new StringWriter()) {

            serialize(entity, sw);
            return sw.toString();
        } catch (IOException | JAXBException e) {
            throw new SerializationException(e);
        }
    }

    private static void serialize(JAXBElement document, Writer writer) throws JAXBException {
        Class<?> clazz = document.getValue().getClass();

        JAXBContext context = JAXBContext.newInstance(clazz.getPackage().getName());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(document, writer);
    }
}
