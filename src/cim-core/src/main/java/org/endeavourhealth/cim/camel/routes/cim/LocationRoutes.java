package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.administrative.GetLocationProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LocationRoutes extends BaseRouteBuilder {
	public static final String GET_LOCATION_ROUTE = "GetLocation";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_LOCATION_ROUTE)
            .process(new GetLocationProcessor());
    }
}
