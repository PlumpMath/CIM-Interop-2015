package org.endeavourhealth.cim.camel.processors.demographics;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.hl7.fhir.instance.model.Subscription;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluateSubscriptionsProcessor implements org.apache.camel.Processor {
    private final HashMap<String, Subscription> _subscriptions = new HashMap<>();

    @Override
    public void process(Exchange exchange) throws Exception {
        String patientId = ExchangeHelper.getInBodyString(exchange);
        String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);

        ArrayList<String> subscriberCallbacks = new ArrayList<>();

        for(Subscription subscription : _subscriptions.values()) {
            subscriberCallbacks.add(subscription.getChannel().getEndpoint());
        }

        if (subscriberCallbacks.size() > 0) {
            IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
            String requestBody = dataManager.getFullRecord(odsCode, patientId);

            ExchangeHelper.setInBodyString(exchange, requestBody);
        }

        ExchangeHelper.setInHeaderString(exchange, CIMHeaderKey.Callbacks, subscriberCallbacks);
    }

    public void add(Subscription subscription) {
        _subscriptions.put(subscription.getId(), subscription);
    }
}
