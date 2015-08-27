package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.hl7.fhir.instance.model.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EvaluateSubscriptionsProcessor implements org.apache.camel.Processor {
    private final HashMap<String, Subscription> _subscriptions = new HashMap<>();

    @Override
    public void process(Exchange exchange) throws Exception {
        String patientId = ExchangeHelper.getInBodyString(exchange);
        String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);

        ArrayList<String> subscriberCallbacks = new ArrayList<>();

        for(Subscription subscription : _subscriptions.values()) {
            subscriberCallbacks.add(subscription.getChannel().getEndpoint());
        }

        if (subscriberCallbacks.size() > 0) {
            IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
            String requestBody = dataManager.getFullRecord(odsCode, patientId);

            ExchangeHelper.setInBodyString(exchange, requestBody);
        }

        ExchangeHelper.setInHeaderString(exchange, HeaderKey.Callbacks, subscriberCallbacks);
    }

    public void add(Subscription subscription) {
        _subscriptions.put(subscription.getId(), subscription);
    }
}
