package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.PropertyKey;

@SuppressWarnings("unused")
public class RouteWrapper extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "RouteWrapper";

    @Override
    public void configureRoute() throws Exception {
        from(direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
			.to(direct(ComponentRouteName.LOAD_DATA_PROTOCOL))
			.to(direct(ComponentRouteName.PAYLOAD_VALIDATION))
			.to(direct(ComponentRouteName.WRAPPED_ROUTE_CALLBACK))
			.to(direct(ComponentRouteName.DATA_AGGREGATOR))
			.wireTap(direct(ComponentRouteName.AUDIT))
				.setProperty(PropertyKey.TapLocation, constant("Outbound"))
			.end()
			.to(direct(ComponentRouteName.RESPONSE));
    }
}
