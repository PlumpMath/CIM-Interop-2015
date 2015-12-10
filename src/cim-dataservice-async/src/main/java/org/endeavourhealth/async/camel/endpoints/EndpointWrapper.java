package org.endeavourhealth.async.camel.endpoints;

import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;

@SuppressWarnings("unused")
public class EndpointWrapper extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "EndpointWrapper";

    @Override
    public void configureRoute() throws Exception {
        from(direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
				.convertBodyTo(String.class)
				.to(direct(ComponentRouteName.FHIR_VALIDATION))
				.wireTap(direct(ComponentRouteName.AUDIT))
					.newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
				.end()
				// .to(direct(ComponentRouteName.SECURITY)) api_key based - needs replacing?
				.to(direct(ComponentRouteName.LOAD_DATA_PROTOCOL))
				.to(direct(ComponentRouteName.WRAPPED_ROUTE_CALLBACK));
    }
}
