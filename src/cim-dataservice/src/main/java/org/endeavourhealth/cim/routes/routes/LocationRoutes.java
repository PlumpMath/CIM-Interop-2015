package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.administrative.GetLocationProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class LocationRoutes extends CIMRouteBuilder {
	public static final String GET_LOCATION_ROUTE = "GetLocation";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_LOCATION_ROUTE)
            .process(new GetLocationProcessor());
    }
}
