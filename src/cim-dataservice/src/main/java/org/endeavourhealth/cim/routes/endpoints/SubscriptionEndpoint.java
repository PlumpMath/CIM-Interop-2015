package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.subscription.AddSubscription;

public class SubscriptionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/service/{serviceId}/subscription")
            .description("Change subscription service")

        .put("/{subscriptionId}")
            .route()
            .routeId("AddSubscriptionToServicePatient")
            .description("Add subscription to patient changes")
            .setHeader("MessageRouterRecipient", constant("direct:AddSubscription"))
            .to("direct:CIMCore")
        .endRest();

        from("direct:AddSubscription")
            .routeId("AddSubscription")
            .process(new AddSubscription());
    }
}
