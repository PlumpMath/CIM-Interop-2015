package org.endeavourhealth.common.camel.routes;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.PropertyKey;

@SuppressWarnings("unused")
public class MessageRouter extends BaseRouteBuilder {
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
