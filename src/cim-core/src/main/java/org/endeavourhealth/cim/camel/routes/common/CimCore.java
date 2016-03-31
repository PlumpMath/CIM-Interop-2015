package org.endeavourhealth.cim.camel.routes.common;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;

import java.util.UUID;

@SuppressWarnings("unused")
public class CimCore extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "CIMCore";

    @Override
    public void configureRoute() throws Exception {

        from(BaseRouteBuilder.direct(ROUTE_NAME))
            .routeId(ROUTE_NAME)
            .choice()
                .when(simple("${exchangeProperty." + PropertyKey.WrappedRouteCallback + "} != null"))
					.to(BaseRouteBuilder.direct(ComponentRouteName.HEADER_VALIDATION))
					.to(BaseRouteBuilder.direct(ComponentRouteName.SECURITY))
					.setProperty(PropertyKey.TapLocation, constant("Inbound"))
					.wireTap(BaseRouteBuilder.direct(ComponentRouteName.AUDIT))
					.end()
					.to(BaseRouteBuilder.direct(ComponentRouteName.LOAD_DATA_PROTOCOL))
					.to(BaseRouteBuilder.direct(ComponentRouteName.PAYLOAD_VALIDATION))
					.to(BaseRouteBuilder.direct(ComponentRouteName.WRAPPED_ROUTE_CALLBACK))
					.to(BaseRouteBuilder.direct(ComponentRouteName.DATA_AGGREGATOR))
					.to(BaseRouteBuilder.direct(ComponentRouteName.DATA_PROTOCOL_FILTER))
					.to(BaseRouteBuilder.direct(ComponentRouteName.RESPONSE))
                .otherwise()
					.log(LoggingLevel.ERROR, "No destination for message route");
    }
}
