package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;

import java.io.InputStream;
import java.util.UUID;

public class AddSubscription implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        UUID subscriptionId = UUID.fromString((String)exchange.getIn().getHeader("id"));
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        String message = "";
        Object body = exchange.getIn().getBody();
        if (body instanceof String)
            message = (String)exchange.getIn().getBody();
        if (body instanceof InputStream)
            message = IOUtils.toString((InputStream) body);

        PollerProcessor.getInstance().addSubscription(subscriptionId, odsCode, message);

    }
}
