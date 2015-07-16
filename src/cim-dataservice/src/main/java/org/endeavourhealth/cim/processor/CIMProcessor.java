package org.endeavourhealth.cim.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.UUID;

public abstract class CIMProcessor implements Processor {

    protected String getInHeaderString(Exchange exchange, String headerKey) {
        return getInHeader(exchange, headerKey, String.class);
    }

    protected UUID getInHeaderUUID(Exchange exchange, String headerKey) {
        return UUID.fromString(getInHeaderString(exchange, headerKey));
    }

    protected ArrayList getInHeaderArray(Exchange exchange, String headerKey) {
        Object object = getInHeader(exchange, headerKey);

        if (object == null)
            return null;

        if (object.getClass().equals(ArrayList.class))
            return (ArrayList)object;

        ArrayList arrayList = new ArrayList();
        arrayList.add(object);
        return arrayList;
    }

    protected <T> T getInHeader(Exchange exchange, String headerKey, Class<T> type) {
        return exchange.getIn().getHeader(headerKey, type);
    }

    protected Object getInHeader(Exchange exchange, String headerKey) {
        return exchange.getIn().getHeader(headerKey);
    }

    protected void setInBody(Exchange exchange, String body) {
        exchange.getIn().setBody(body);
    }
}
