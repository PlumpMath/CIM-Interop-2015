package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.cim.camel.routes.LocationRoutes;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LocationEndpoints extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}/Location";

        final String ID_URI = "/{id}";

        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(BaseRouteBuilder.direct(LocationRoutes.GET_LOCATION_ROUTE))
        .endRest();
    }
}
