package org.endeavourhealth.cim.common.serializer;

import org.endeavourhealth.cim.common.repository.common.model.PartialDateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public class XmlHelper {

    public static XMLGregorianCalendar convertDateTimeToXmlDate(Date date) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar dateTimeInXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return dateTimeInXml;
    }

    public static PartialDateTime convertXmlDateToPartialDatetime(XMLGregorianCalendar date) throws DatatypeConfigurationException {

        if (date == null)
            return null;

        return new PartialDateTime(date.getYear(), date.getMonth(), date.getDay());
    }

    public static LocalDateTime convertXmlDateTimeToLLocalDateTime(XMLGregorianCalendar input) {
        return LocalDateTime.of(input.getYear(), input.getMonth(), input.getDay(), input.getHour(), input.getMinute(), input.getSecond());
    }
}
