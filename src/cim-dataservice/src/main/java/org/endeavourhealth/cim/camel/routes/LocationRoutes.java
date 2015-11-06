package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.administrative.GetLocationProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LocationRoutes extends BaseRouteBuilder {
	public static final String GET_LOCATION_ROUTE = "GetLocation";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(CimCore.ROUTE_NAME, GET_LOCATION_ROUTE)
            .process(new GetLocationProcessor());
    }
}
