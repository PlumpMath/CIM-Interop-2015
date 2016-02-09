package org.endeavourhealth.cim.camel.helpers;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.exceptions.InvalidParamException;
import org.endeavourhealth.cim.exceptions.MissingParamException;
import org.endeavourhealth.cim.repository.utils.TextUtils;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Resource;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class ExchangeHelper {

    public static String getInHeaderString(Exchange exchange, String headerKey) {
        return getInHeader(exchange, headerKey, String.class);
    }

    public static String getInHeaderString(Exchange exchange, String headerKey, Boolean required) throws MissingParamException {
        if (required)
            if (!hasInHeader(exchange, headerKey))
                throw new MissingParamException(headerKey);

        return getInHeaderString(exchange, headerKey);
    }

    public static TokenSearchParameter getInHeaderToken(Exchange exchange, String headerKey) {
        String tokenString = getInHeaderString(exchange, headerKey);

        if (TextUtils.isNullOrTrimmedEmpty(tokenString))
            return null;

        return new TokenSearchParameter(tokenString);
    }

    public static Boolean hasInHeader(Exchange exchange, String headerKey) {
        return exchange.getIn().getHeaders().containsKey(headerKey);
    }

//    public static UUID getInHeaderUUID(Exchange exchange, String headerKey, Boolean required) throws InvalidParamException, MissingParamException {
//        String value = getInHeaderString(exchange, headerKey, required);
//
//        if (value == null)
//            return null;
//
//        try {
//            return UUID.fromString(value);
//        } catch (Exception e) {
//            throw new InvalidParamException(e);
//        }
//    }

    @SuppressWarnings("unchecked")
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

    public static String getEndpointPath(Exchange exchange) throws UnsupportedEncodingException {
		String path = exchange.getIn().getHeader("CamelHttpPath").toString();
		Object query = exchange.getIn().getHeader("CamelHttpQuery");

		if (query != null && !"".equals(query)) {
			path += "?" + java.net.URLDecoder.decode(query.toString(), "UTF-8");
		}

		return path;
    }

    public static Date getInHeaderDate(Exchange exchange, String headerKey) {
        return DateUtils.Parse(getInHeaderString(exchange, headerKey));
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

    public static void setOutBodyString(Exchange exchange, String body) {
        exchange.getOut().setBody(body, String.class);
    }

    public static String getInBodyString(Exchange exchange) throws Exception {
        if (exchange.getIn().getBody() instanceof InputStream)
            return IOUtils.toString((InputStream)exchange.getIn().getBody());

        return (String)exchange.getIn().getBody();
    }

    public static Object getInBodyObject(Exchange exchange) throws Exception {
        return exchange.getIn().getBody();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Resource> T getInBodyResource(Exchange exchange, Class<T> type, Boolean required) throws Exception {

        String body = getInBodyString(exchange);

        if (TextUtils.isNullOrTrimmedEmpty(body)) {
            if (required)
                throw new MissingParamException("body");
            else
                return null;
        }

        try {
            return (T) new JsonParser().parse(body);
        } catch (Exception e) {
            throw new InvalidParamException(e);
        }
    }
}
