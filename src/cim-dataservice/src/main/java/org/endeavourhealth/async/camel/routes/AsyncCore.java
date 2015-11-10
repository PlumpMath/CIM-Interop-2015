package org.endeavourhealth.async.camel.routes;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class AsyncCore extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "AsyncCore";

    @Override
    public void configureRoute() throws Exception {
        from(direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
            .choice()
                .when(simple("${exchangeProperty." + HeaderKey.MessageRouterCallback + "} != null"))
					.to(direct(ComponentRouteName.DATA_PROTOCOL))
					.to(direct(ComponentRouteName.PAYLOAD_VALIDATION))
					.to(direct(ComponentRouteName.MESSAGE_ROUTER))
					.to(direct(ComponentRouteName.DATA_AGGREGATOR))
					.wireTap(direct(ComponentRouteName.AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
					.end()
						.to(direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
						.to(direct(ComponentRouteName.RESPONSE))
                .otherwise()
					.log(LoggingLevel.ERROR, "No destination for message route");
    }
}
