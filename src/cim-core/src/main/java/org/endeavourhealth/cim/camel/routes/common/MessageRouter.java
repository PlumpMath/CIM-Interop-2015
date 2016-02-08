package org.endeavourhealth.cim.camel.routes.common;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.cim.camel.helpers.BaseRouteBuilder;
import org.endeavourhealth.cim.camel.helpers.ComponentRouteName;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;

@SuppressWarnings("unused")
public class MessageRouter extends BaseRouteBuilder
{
    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.WRAPPED_ROUTE_CALLBACK))
            .routeId(ComponentRouteName.WRAPPED_ROUTE_CALLBACK)
			.choice()
				.when(simple("${exchangeProperty." + PropertyKey.WrappedRouteCallback + "} != null"))
					.recipientList(exchangeProperty(PropertyKey.WrappedRouteCallback))
				.end()
			.otherwise()
				.log(LoggingLevel.ERROR, "No destination for message route")
			.end();
    }
}
