package org.endeavourhealth.common.camel.routes;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;

@SuppressWarnings("unused")
public class MessageRouter extends BaseRouteBuilder {
    @Override
    public void configureRoute() throws Exception {

        from(direct(ComponentRouteName.WRAPPED_ROUTE_CALLBACK))
            .routeId(ComponentRouteName.WRAPPED_ROUTE_CALLBACK)
			.choice()
				.when(simple("${exchangeProperty." + HeaderKey.WrappedRouteCallback + "} != null"))
					.recipientList(exchangeProperty(HeaderKey.WrappedRouteCallback))
				.end()
			.otherwise()
				.log(LoggingLevel.ERROR, "No destination for message route")
			.end();
    }
}
