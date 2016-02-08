package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.routes.cim.BinaryRoutes;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

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
