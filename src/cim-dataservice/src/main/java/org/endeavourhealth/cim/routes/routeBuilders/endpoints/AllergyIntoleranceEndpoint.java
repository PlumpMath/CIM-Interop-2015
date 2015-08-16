package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.processor.clinical.GetAllergyIntolerancesProcessor;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AllergyIntoleranceEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String IMMUNIZATIONS_ROUTE = "/{id}/AllergyIntolerance";

        final String GET_IMMUNIZATIONS_PROCESSOR_ROUTE = "GetAllergyIntolerances";

        rest(BASE_ROUTE)

        .get(IMMUNIZATIONS_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + IMMUNIZATIONS_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_IMMUNIZATIONS_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_IMMUNIZATIONS_PROCESSOR_ROUTE))
            .routeId(GET_IMMUNIZATIONS_PROCESSOR_ROUTE)
            .process(new GetAllergyIntolerancesProcessor());
    }
}
