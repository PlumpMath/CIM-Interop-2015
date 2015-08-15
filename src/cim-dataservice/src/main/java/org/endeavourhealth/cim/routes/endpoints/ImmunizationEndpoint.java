package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.config.Route;
import org.endeavourhealth.cim.processor.clinical.GetImmunizationsProcessor;

@SuppressWarnings("WeakerAccess")
public class ImmunizationEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Patient";
        final String IMMUNIZATIONS_ROUTE = "/{id}/Immunization";

        final String GET_IMMUNIZATIONS_PROCESSOR_ROUTE = "GetImmunizations";

        rest(BASE_ROUTE)

        .get(IMMUNIZATIONS_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + IMMUNIZATIONS_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_IMMUNIZATIONS_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_IMMUNIZATIONS_PROCESSOR_ROUTE))
            .routeId(GET_IMMUNIZATIONS_PROCESSOR_ROUTE)
            .process(new GetImmunizationsProcessor());

    }
}
