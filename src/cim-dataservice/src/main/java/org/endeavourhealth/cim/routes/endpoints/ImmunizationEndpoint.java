package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.event.GetImmunizations;

@SuppressWarnings("WeakerAccess")
public class ImmunizationEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient/{id}/Immunization")
            .description("Immunization rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetImmunizationsEndPoint")
            .description("Get patient immunizations")
            .setHeader(HeaderKey.MessageRouterCallback, constant("direct:GetImmunizationsRoute"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetImmunizationsRoute")
            .routeId("GetImmunizationsRoute")
            .process(new GetImmunizations());

    }
}
