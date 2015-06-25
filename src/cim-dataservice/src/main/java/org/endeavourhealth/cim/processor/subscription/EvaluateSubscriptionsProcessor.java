package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;
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
            IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
            String patientData = dataAdapter.getPatientRecordByPatientId(odsCode, patientUUID);

            // Get patientApi data transformer for service (native format -> FHIR)
            Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
            Bundle bundle = transformer.toFHIRBundle(patientData);
            String body = new JsonParser().composeString(bundle);

            exchange.getIn().setBody(body);
        }

        exchange.getIn().setHeader("Callbacks", subscriberCallbacks);
    }

    public void add(Subscription subscription) {
        _subscriptions.put(subscription.getId(), subscription);
    }
}
