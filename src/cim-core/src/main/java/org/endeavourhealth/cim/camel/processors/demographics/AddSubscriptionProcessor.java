package org.endeavourhealth.cim.camel.processors.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.subscriptions.SubscriptionManager;

public class AddSubscriptionProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        SubscriptionManager.getInstance().addSubscription(odsCode, requestBody, exchange.getContext());
    }
}
