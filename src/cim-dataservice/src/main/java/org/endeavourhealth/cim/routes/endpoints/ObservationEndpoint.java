package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.observation.AddObservation;

public class ObservationEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/service/{serviceId}/observation")
            .description("Observation rest service")

        // Endpoint definitions (GET, PUT, etc)
        .post()
            .route()
            .routeId("CreateObservation")
            .description("Add observation")
            .setHeader("MessageRouterCallback", constant("direct:AddObservation"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:AddObservation")
            .routeId("AddObservation")
            .process(new AddObservation());
    }
}
