package org.endeavourhealth.cim.routes.core;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMCore extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        from(Route.direct(CoreRouteName.CIM_CORE))
            .routeId(CoreRouteName.CIM_CORE)
            .choice()
                .when(simple("${exchangeProperty." + HeaderKey.MessageRouterCallback + "} != null"))
					.to(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION))
					.wireTap(Route.direct(CoreRouteName.CIM_AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
					.end()
					.to(Route.direct(CoreRouteName.CIM_SECURITY))
					.to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL))
					.to(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION))
					.to(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER))
					.to(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR))
					.wireTap(Route.direct(CoreRouteName.CIM_AUDIT))
						.newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
					.end()
					.to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER))
					.to(Route.direct(CoreRouteName.CIM_RESPONSE))
                .otherwise()
					.log(LoggingLevel.ERROR, "No destination for message route");
    }
}
