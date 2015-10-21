package org.endeavourhealth.common.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;

public abstract class BaseRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .to(Route.direct(CoreRouteName.EXCEPTION_HANDLER))
                .handled(true);

        configureRoute();
    }

	public RouteDefinition buildCallbackRoute(String routeName) {
		String callbackRoute = routeName+"Callback";

		from(Route.direct(routeName))
				.routeId(routeName)
				.setProperty(HeaderKey.MessageRouterCallback, constant(Route.direct(callbackRoute)))
				.to(Route.CORE);

		return from(Route.direct(callbackRoute))
				.routeId(callbackRoute);
	}

    public abstract void configureRoute() throws Exception;
}
