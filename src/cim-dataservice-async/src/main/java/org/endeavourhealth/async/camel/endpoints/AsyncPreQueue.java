package org.endeavourhealth.async.camel.endpoints;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;

@SuppressWarnings("unused")
public class AsyncPreQueue extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "AsyncPreQueue";

    @Override
    public void configureRoute() throws Exception {
        from(direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
			.convertBodyTo(String.class)
			.to(direct(ComponentRouteName.HEADER_VALIDATION))
			.wireTap(direct(ComponentRouteName.AUDIT))
				.newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
			.end()
			.to(direct(ComponentRouteName.SECURITY));
    }
}
