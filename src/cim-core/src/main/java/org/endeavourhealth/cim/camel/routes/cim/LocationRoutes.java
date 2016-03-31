package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.administrative.GetLocationProcessor;
import org.endeavourhealth.cim.camel.routes.common.CimCore;
import org.endeavourhealth.cim.camel.helpers.HttpVerb;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LocationRoutes extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String GET_LOCATION_ROUTE = "GetLocation";

        final String BASE_PATH = "/{odsCode}/Location";
        final String ID_URI = "/{id}";

        // endpoints
        rest(BASE_PATH)

        .get(ID_URI)
            .route()
            .routeId(HttpVerb.GET + BASE_PATH + ID_URI)
            .to(BaseRouteBuilder.direct(GET_LOCATION_ROUTE))
        .endRest();

        // routes
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_LOCATION_ROUTE)
                .process(new GetLocationProcessor());
    }
}
