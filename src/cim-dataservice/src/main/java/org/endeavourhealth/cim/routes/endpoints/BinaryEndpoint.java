package org.endeavourhealth.cim.routes.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.clinical.GetBinaryObjectProcessor;
import org.endeavourhealth.cim.routes.config.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryEndpoint extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        final String BASE_ROUTE = "/{odsCode}/Binary";
        final String BINARY_ROUTE = "/{id}";

        final String GET_BINARY_PROCESSOR_ROUTE = "GetBinaryObject";

        rest(BASE_ROUTE)

        .get(BINARY_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + BINARY_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(GET_BINARY_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(GET_BINARY_PROCESSOR_ROUTE))
            .routeId(GET_BINARY_PROCESSOR_ROUTE)
            .process(new GetBinaryObjectProcessor());
    }
}
