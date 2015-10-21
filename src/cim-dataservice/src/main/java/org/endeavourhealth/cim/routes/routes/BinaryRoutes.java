package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.clinical.GetBinaryObjectProcessor;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryRoutes extends BaseRouteBuilder {
	public static final String GET_BINARY_ROUTE = "GetBinaryObject";

    @Override
    public void configureRoute() throws Exception {
        buildCallbackRoute(GET_BINARY_ROUTE)
            .process(new GetBinaryObjectProcessor());
    }
}
