package org.endeavourhealth.cim.camel.routes;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;

@SuppressWarnings("unused")
public class CimCore extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "CIMCore";

    @Override
    public void configureRoute() throws Exception {

        from(BaseRouteBuilder.direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
            .choice()
                .when(simple("${exchangeProperty." + HeaderKey.MessageRouterCallback + "} != null"))
					.to(BaseRouteBuilder.direct(ComponentRouteName.HEADER_VALIDATION))
					.wireTap(BaseRouteBuilder.direct(ComponentRouteName.AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
					.end()
					.to(BaseRouteBuilder.direct(ComponentRouteName.SECURITY))
					.to(BaseRouteBuilder.direct(ComponentRouteName.DATA_PROTOCOL))
					.to(BaseRouteBuilder.direct(ComponentRouteName.PAYLOAD_VALIDATION))
					.to(BaseRouteBuilder.direct(ComponentRouteName.MESSAGE_ROUTER))
					.to(BaseRouteBuilder.direct(ComponentRouteName.DATA_AGGREGATOR))
					.wireTap(BaseRouteBuilder.direct(ComponentRouteName.AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
					.end()
						.to(BaseRouteBuilder.direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
						.to(BaseRouteBuilder.direct(ComponentRouteName.RESPONSE))
                .otherwise()
					.log(LoggingLevel.ERROR, "No destination for message route");
    }
}
