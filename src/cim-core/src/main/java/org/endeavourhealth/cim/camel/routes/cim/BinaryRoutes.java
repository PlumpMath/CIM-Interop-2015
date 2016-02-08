package org.endeavourhealth.cim.camel.routes.cim;

import org.endeavourhealth.cim.camel.processors.clinical.GetBinaryObjectProcessor;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryRoutes extends BaseRouteBuilder {
	public static final String GET_BINARY_ROUTE = "GetBinaryObject";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_BINARY_ROUTE)
            .process(new GetBinaryObjectProcessor());
    }
}
