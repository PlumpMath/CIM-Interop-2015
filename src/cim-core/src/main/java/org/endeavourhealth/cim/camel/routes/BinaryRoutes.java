package org.endeavourhealth.cim.camel.routes;

import org.endeavourhealth.cim.processor.clinical.GetBinaryObjectProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryRoutes extends BaseRouteBuilder {
	public static final String GET_BINARY_ROUTE = "GetBinaryObject";

    @Override
    public void configureRoute() throws Exception {
        buildWrappedRoute(CimCore.ROUTE_NAME, GET_BINARY_ROUTE)
            .process(new GetBinaryObjectProcessor());
    }
}
