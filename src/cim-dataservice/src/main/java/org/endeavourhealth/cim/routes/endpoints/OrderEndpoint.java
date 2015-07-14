package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.orders.RequestOrderProcessor;

@SuppressWarnings("WeakerAccess")
public class OrderEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("{odsCode}/Order")
            .description("Order rest service")

        // Endpoint definitions (GET, PUT, etc)
        .post()
            .route()
            .routeId("PostOrder")
            .description("Post an order request")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:RequestOrder"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:RequestOrder")
            .routeId("RequestOrder")
            .process(new RequestOrderProcessor());
    }
}
