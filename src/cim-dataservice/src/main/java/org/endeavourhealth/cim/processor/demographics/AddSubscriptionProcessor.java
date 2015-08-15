package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.subscriptions.SubscriptionManager;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Subscription;

import java.io.InputStream;

public class AddSubscriptionProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        SubscriptionManager.getInstance().addSubscription(odsCode, requestBody, exchange.getContext());
    }
}
