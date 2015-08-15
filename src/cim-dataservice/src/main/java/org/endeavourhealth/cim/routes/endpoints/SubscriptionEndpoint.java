package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.demographics.AddSubscriptionProcessor;

@SuppressWarnings("WeakerAccess")
public class SubscriptionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/{odsCode}/Subscription")
            .description("Change subscription service")

        .put("/{id}")
            .route()
            .routeId("AddSubscriptionToServicePatient")
            .description("Add subscription to patient changes")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:AddSubscription"))
            .to("direct:CIMCore")
        .endRest();

        from("direct:AddSubscription")
            .routeId("AddSubscription")
            .process(new AddSubscriptionProcessor());
    }
}
