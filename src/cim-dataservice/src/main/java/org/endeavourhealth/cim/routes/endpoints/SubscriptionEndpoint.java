package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.subscription.AddSubscriptionProcessor;

public class SubscriptionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/{odsCode}/Subscription")
            .description("Change subscription service")

        .put("/{id}")
            .route()
            .routeId("AddSubscriptionToServicePatient")
            .description("Add subscription to patient changes")
            .setHeader("MessageRouterCallback", constant("direct:AddSubscription"))
            .to("direct:CIMCore")
        .endRest();

        from("direct:AddSubscription")
            .routeId("AddSubscription")
            .process(new AddSubscriptionProcessor());
    }
}
