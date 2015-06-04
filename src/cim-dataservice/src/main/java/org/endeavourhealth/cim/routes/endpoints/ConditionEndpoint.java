package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.observation.AddCondition;

public class ConditionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient/{patientId}/Condition")
            .description("Condition rest service")

        // Endpoint definitions (GET, PUT, etc)
        .post()
            .route()
            .routeId("CreateCondition")
            .description("Add condition")
            .setHeader("MessageRouterCallback", constant("direct:AddCondition"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:AddCondition")
            .routeId("AddCondition")
            .process(new AddCondition());
    }
}
