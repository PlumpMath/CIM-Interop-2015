package org.endeavourhealth.cim.transform;

import org.hl7.fhir.instance.model.Resource;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

public class TransformHelper {
    public static Date toDate(XMLGregorianCalendar value) {
        if (value == null) {
            return null;
        }
        return value.toGregorianCalendar().getTime();
    }

    public static <T extends Resource> String createResourceReference(Class<T> resourceClass, String id) {
        return resourceClass.getSimpleName() + "/" + id;
    }
}
