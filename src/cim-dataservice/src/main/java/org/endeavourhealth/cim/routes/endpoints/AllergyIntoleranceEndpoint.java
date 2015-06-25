package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.processor.event.GetAllergyIntolerances;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AllergyIntoleranceEndpoint extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Endpoint root URI
        rest("/{odsCode}/Patient/{id}/AllergyIntolerance")
            .description("Allergy/Intolerance rest service")

        // Endpoint definitions (GET, PUT, etc)
        .get()
            .route()
            .routeId("GetAllergyIntolerancesEndPoint")
            .description("Get patient allergies & intolerances")
            .setHeader("MessageRouterCallback", constant("direct:GetAllergyIntolerancesRoute"))
            .to("direct:CIMCore")
        .endRest();

        // Message router callback routes
        from("direct:GetAllergyIntolerancesRoute")
            .routeId("GetAllergyIntolerancesRoute")
            .process(new GetAllergyIntolerances());

    }
}
