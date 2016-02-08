package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.subscriptions.SubscriptionManager;
import org.endeavourhealth.common.core.HeaderKey;

public class AddSubscriptionProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode);
        String requestBody = ExchangeHelper.getInBodyString(exchange);

        SubscriptionManager.getInstance().addSubscription(odsCode, requestBody, exchange.getContext());
    }
}
