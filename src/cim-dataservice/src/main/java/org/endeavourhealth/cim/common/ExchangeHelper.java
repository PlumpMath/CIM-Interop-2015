package org.endeavourhealth.cim.common;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class ExchangeHelper {

    public static String getInHeaderString(Exchange exchange, String headerKey) {
        return getInHeader(exchange, headerKey, String.class);
    }

    public static UUID getInHeaderUUID(Exchange exchange, String headerKey) {
        return UUID.fromString(getInHeaderString(exchange, headerKey));
    }

    public static ArrayList getInHeaderArray(Exchange exchange, String headerKey) {
        Object object = getInHeader(exchange, headerKey);

        if (object == null)
            return null;

        if (object.getClass().equals(ArrayList.class))
            return (ArrayList)object;

        ArrayList arrayList = new ArrayList();
        arrayList.add(object);
        return arrayList;
    }

    public static <T> T getInHeader(Exchange exchange, String headerKey, Class<T> type) {
        return exchange.getIn().getHeader(headerKey, type);
    }

    public static Object getInHeader(Exchange exchange, String headerKey) {
        return exchange.getIn().getHeader(headerKey);
    }

    public static void setInHeaderString(Exchange exchange, String headerKey, Object headerValue) {
        exchange.getIn().setHeader(headerKey, headerValue);
    }

    public static void setInBodyString(Exchange exchange, String body) {
        exchange.getIn().setBody(body, String.class);
    }

    public static String getInBodyString(Exchange exchange) throws Exception {
        return IOUtils.toString((InputStream)exchange.getIn().getBody());
    }

    public static <T extends Resource> T getInBodyResource(Exchange exchange, Class<T> type) throws Exception {
        String body = getInBodyString(exchange);
        return (T)new JsonParser().parse(body);
    }
}
