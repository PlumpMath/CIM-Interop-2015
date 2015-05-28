package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.CIMRouteBuilder;
import org.endeavourhealth.cim.common.IDataAdapter;
import org.endeavourhealth.cim.common.ITransformer;
import org.endeavourhealth.cim.transformer.TransformerFactory;

import java.util.UUID;

public class AddSubscription implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String subscriptionId = (String)exchange.getIn().getHeader("SubscriptionId");
        int serviceId = Integer.parseInt((String) exchange.getIn().getHeader("serviceId"));

        String subscriptionRequestJSON = (String)exchange.getIn().getBody();
//        CIMSubscription cimSubscription = FromJSON(subscriptionRequestJSON);

        // Generate route logic based on subscription data
        String routeId = "Subscription "; // + cimSubscription.SubscriptionId;
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);

        // Call appropriate route builder
//        switch (cimSubscription.SubscriptionType) {
//            case Patient:
                // Routebuilder to poll internal GetPatientChanges call
                exchange.getContext().addRoutes(new ChangePollerRouteBuilder(routeId, dataAdapter));
//                break;
//            default:

//        }

    }
}
