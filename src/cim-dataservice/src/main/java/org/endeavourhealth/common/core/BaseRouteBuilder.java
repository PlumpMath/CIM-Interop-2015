package org.endeavourhealth.common.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

public abstract class BaseRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .to(direct(ComponentRouteName.EXCEPTION_HANDLER))
                .handled(true);

        configureRoute();
    }

	protected static String direct(String routeName) {
		return "direct:"+routeName;
	}

	public RouteDefinition buildWrappedRoute(String coreName, String routeName) {
		String wrappedRoute = routeName + "Internal";

		from(direct(routeName))
				.routeId(routeName)
				.setProperty(PropertyKey.WrappedRouteCallback, constant(direct(wrappedRoute)))
				.to(direct(coreName));

		return from(direct(wrappedRoute))
				.routeId(wrappedRoute);
	}

    public abstract void configureRoute() throws Exception;

}
