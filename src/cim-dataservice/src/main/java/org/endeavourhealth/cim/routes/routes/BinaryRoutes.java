package org.endeavourhealth.cim.routes.routes;

import org.endeavourhealth.cim.processor.clinical.GetBinaryObjectProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinaryRoutes extends CIMRouteBuilder {
	public static final String GET_BINARY_ROUTE = "GetBinaryObject";

    @Override
    public void configureRoute() throws Exception {
        buildCimCallbackRoute(GET_BINARY_ROUTE)
            .process(new GetBinaryObjectProcessor());
    }
}
