package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.routes.BinaryRoutes;

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
            .to(direct(BinaryRoutes.GET_BINARY_ROUTE))
        .endRest();
    }
}
