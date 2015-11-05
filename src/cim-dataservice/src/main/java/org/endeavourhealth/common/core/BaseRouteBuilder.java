package org.endeavourhealth.common.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.rest.RestDefinition;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.common.routes.common.CoreRouteName;
import org.endeavourhealth.common.routes.common.Route;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

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

	public RouteDefinition buildRabbitCallbackRoute(String basePath, String verb, String routeName) throws RepositoryException {
		return buildRabbitCallbackRoute(basePath, "", verb, routeName);
	}

	public RouteDefinition buildRabbitCallbackRoute(String basePath, String uri, String verb, String routeName) throws RepositoryException {
		RestDefinition restDef;

		// Rest endpoint to rabbit
		if (HttpVerb.POST.equals(verb))
			restDef = rest(basePath).post(uri);
		else if (HttpVerb.GET.equals(verb))
			restDef = rest(basePath).get(uri);
		else if (HttpVerb.PUT.equals(verb))
			restDef = rest(basePath).put(uri);
		else if (HttpVerb.DELETE.equals(verb))
			restDef = rest(basePath).delete(uri);
		else
			throw new IllegalArgumentException("Invalid HTTP verb");

		restDef
			.route()
			.routeId(verb + basePath)
			.to(rabbitQueue(routeName))
		.endRest();

		// Rabbit reader to route to rabbit response
		from(rabbitQueue(routeName))
				.routeId("RMQ_To_Route")
				.removeHeaders("rabbitmq.*")	// Needed to prevent overriding routingKey in camel endpoint
				.convertBodyTo(String.class)
				.to(Route.direct(routeName))
				.to(rabbitQueue(routeName + "_Response"));

		// Rabbit response to callback
		from(rabbitQueue(routeName + "_Response"))
				.routeId("Route_To_RMQ_Response")
				.removeHeaders("rabbitmq.*")	// Needed to prevent overriding routingKey in camel endpoint
				.removeHeaders("Camel*")
				.convertBodyTo(String.class)
				.recipientList(simple("${header.response_uri}"));

		// Route
		return buildCallbackRoute(routeName);
	}

    public abstract void configureRoute() throws Exception;

	private String rabbitQueue(String routeName) throws RepositoryException {
		String contextName = getContext().getName();
		final String RMQ_EXCHANGE = Registry.Instance().getRabbitHost(contextName) + "/" + getContext().getName();
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&"+Registry.Instance().getRabbitLogon(contextName);
		final String RMQ_ROUTING = "&queue=m." + routeName + "&routingKey=m." + routeName;

		return "rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS + RMQ_ROUTING;
	}
}
