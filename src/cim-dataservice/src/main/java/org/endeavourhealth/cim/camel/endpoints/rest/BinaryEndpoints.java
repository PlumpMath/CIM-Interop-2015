package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.cim.camel.routes.BinaryRoutes;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Binary";

        final String BINARY_URI = "/{id}";

        rest(BASE_PATH)

        .get(BINARY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BINARY_URI)
            .to(BaseRouteBuilder.direct(BinaryRoutes.GET_BINARY_ROUTE))
        .endRest();
    }
}
