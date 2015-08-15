package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.clinical.AddConditionProcessor;
import org.endeavourhealth.cim.processor.clinical.GetConditionsProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ConditionEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient/{id}/Condition")
            .description("Condition rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetConditionsEndPoint")
            .description("Get patient conditions")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetConditionsRoute"))
            .to("direct:CIMCore")
        .endRest()

        .post()
            .route()
            .routeId("PostConditionEndPoint")
            .description("Add condition")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:AddConditionRoute"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetConditionsRoute")
            .routeId("GetConditionsRoute")
            .process(new GetConditionsProcessor());

        from("direct:AddConditionRoute")
            .routeId("AddConditionRoute")
            .process(new AddConditionProcessor());
    }
}
