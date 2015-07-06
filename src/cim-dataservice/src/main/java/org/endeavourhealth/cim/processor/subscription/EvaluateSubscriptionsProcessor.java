package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.Exchange;
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
        UUID patientUUID = (UUID)exchange.getIn().getBody();
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        // Check through subscriptions for any interested in this patient
        ArrayList<String> subscriberCallbacks = new ArrayList<>();

        for(Subscription subscription : _subscriptions.values()) {
            subscriberCallbacks.add(subscription.getChannel().getEndpoint());
        }

        if (subscriberCallbacks.size() > 0) {
            IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
            String body = dataManager.getPatientRecordByPatientId(odsCode, patientUUID);
            exchange.getIn().setBody(body);
        }

        exchange.getIn().setHeader("Callbacks", subscriberCallbacks);
    }

    public void add(Subscription subscription) {
        _subscriptions.put(subscription.getId(), subscription);
    }
}
