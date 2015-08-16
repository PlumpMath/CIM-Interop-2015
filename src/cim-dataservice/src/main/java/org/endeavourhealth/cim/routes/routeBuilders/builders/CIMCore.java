package org.endeavourhealth.cim.routes.routeBuilders.builders;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.routes.common.CoreRouteName;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings("unused")
public class CIMCore extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(Route.direct(CoreRouteName.CIM_CORE))
            .routeId(CoreRouteName.CIM_CORE)
            .choice()
                .when(simple("${header." + HeaderKey.MessageRouterCallback + "} == null"))
                    .log(LoggingLevel.ERROR, "No destination for message route")
                    .stop()
                .otherwise()
                    .to(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION));

        from(Route.direct(CoreRouteName.CIM_HEADER_VALIDATION_RESULT))
            .routeId(CoreRouteName.CIM_HEADER_VALIDATION_RESULT)
            .wireTap(Route.direct(CoreRouteName.CIM_AUDIT))
                .newExchangeHeader(HeaderKey.TapLocation, constant("Inbound"))
            .end()
            .to(Route.direct(CoreRouteName.CIM_SECURITY));

        from(Route.direct(CoreRouteName.CIM_SECURITY_RESULT))
            .routeId(CoreRouteName.CIM_SECURITY_RESULT)
            .to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL));

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_RESULT))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL_RESULT)
            .to(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION));

        from(Route.direct(CoreRouteName.CIM_PAYLOAD_VALIDATION_RESULT))
            .routeId(CoreRouteName.CIM_PAYLOAD_VALIDATION_RESULT)
            .to(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER));

        from(Route.direct(CoreRouteName.CIM_MESSAGE_ROUTER_RESULT))
            .routeId(CoreRouteName.CIM_MESSAGE_ROUTER_RESULT)
            .to(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR));

        from(Route.direct(CoreRouteName.CIM_DATA_AGGREGATOR_RESULT))
            .routeId(CoreRouteName.CIM_DATA_AGGREGATOR_RESULT)
                .wireTap(Route.direct(CoreRouteName.CIM_AUDIT))
                .newExchangeHeader(HeaderKey.TapLocation, constant("Outbound"))
            .end()
            .to(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER));

        from(Route.direct(CoreRouteName.CIM_DATA_PROTOCOL_FILTER_RESULT))
            .routeId(CoreRouteName.CIM_DATA_PROTOCOL_FILTER_RESULT)
            .to(Route.direct(CoreRouteName.CIM_RESPONSE));
    }
}
