package org.endeavourhealth.common.routes.core;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings("unused")
public class Core extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CORE))
            .routeId(CoreRouteName.CORE)
            .choice()
                .when(simple("${exchangeProperty." + HeaderKey.MessageRouterCallback + "} != null"))
					.to(Route.direct(CoreRouteName.HEADER_VALIDATION))
					.wireTap(Route.direct(CoreRouteName.AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
					.end()
					.to(Route.direct(CoreRouteName.SECURITY))
					.to(Route.direct(CoreRouteName.DATA_PROTOCOL))
					.to(Route.direct(CoreRouteName.PAYLOAD_VALIDATION))
					.to(Route.direct(CoreRouteName.MESSAGE_ROUTER))
					.to(Route.direct(CoreRouteName.DATA_AGGREGATOR))
					.wireTap(Route.direct(CoreRouteName.AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
					.end()
					.to(Route.direct(CoreRouteName.DATA_PROTOCOL_FILTER))
					.to(Route.direct(CoreRouteName.RESPONSE))
                .otherwise()
					.log(LoggingLevel.ERROR, "No destination for message route");
    }
}
