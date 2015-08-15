package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.subscriptions.SubscriptionManager;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Subscription;

import java.io.InputStream;

public class AddSubscriptionProcessor implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String odsCode = (String) exchange.getIn().getHeader(HeaderKey.OdsCode);

        String message = "";
        Object body = exchange.getIn().getBody();
        if (body instanceof String)
            message = (String)exchange.getIn().getBody();
        if (body instanceof InputStream)
            message = IOUtils.toString((InputStream) body);

        Subscription subscription = (Subscription)new JsonParser().parse(message);

        SubscriptionManager.getInstance().addSubscription(odsCode, subscription, exchange.getContext());
    }
}
