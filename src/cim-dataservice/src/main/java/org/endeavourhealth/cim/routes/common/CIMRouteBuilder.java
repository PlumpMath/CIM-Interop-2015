package org.endeavourhealth.cim.routes.common;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;

public abstract class CIMRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .to(Route.direct(CoreRouteName.CIM_EXCEPTION_HANDLER))
                .handled(true);

        configureRoute();
    }

	public RouteDefinition buildCimCallbackRoute(String routeName) {
		String callbackRoute = routeName+"Callback";

		from(Route.direct(routeName))
				.routeId(routeName)
				.setProperty(HeaderKey.MessageRouterCallback, constant(Route.direct(callbackRoute)))
				.to(Route.CIM_CORE);

		return from(Route.direct(callbackRoute))
				.routeId(callbackRoute);
	}

    public abstract void configureRoute() throws Exception;
}
