package org.endeavourhealth.cim.camel.endpoints;

import org.endeavourhealth.cim.camel.routes.cim.LocationRoutes;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

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
