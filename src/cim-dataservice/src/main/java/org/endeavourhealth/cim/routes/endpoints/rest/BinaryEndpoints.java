package org.endeavourhealth.cim.routes.endpoints.rest;

import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;
import org.endeavourhealth.cim.routes.routes.BinaryRoutes;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryEndpoints extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Binary";

        final String BINARY_URI = "/{id}";

        rest(BASE_PATH)

        .get(BINARY_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + BINARY_URI)
            .to(Route.direct(BinaryRoutes.GET_BINARY_ROUTE))
        .endRest();
    }
}