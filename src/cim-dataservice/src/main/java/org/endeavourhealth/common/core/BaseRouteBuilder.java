package org.endeavourhealth.common.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.endeavourhealth.common.camel.QueueReader;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.rabbit.RabbitConfig;
import org.endeavourhealth.core.repository.rabbit.RabbitConfigRepository;

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

	protected String queueWriter(String routeName) throws RepositoryException {
		String inboundQueueRoute = routeName+"Queue";

		from(direct(inboundQueueRoute))
			.routeId(inboundQueueRoute)
			.setHeader(QueueReader.DESTINATION_ROUTE, constant(direct(routeName)))
			.to(rabbitQueue());

		return direct(inboundQueueRoute);
	}

	public RouteDefinition buildCallbackRoute(String coreName, String routeName) {
		String callbackRoute = routeName+"Callback";

		from(direct(routeName))
				.routeId(routeName)
				.setProperty(HeaderKey.MessageRouterCallback, constant(direct(callbackRoute)))
				.to(direct(coreName));

		return from(direct(callbackRoute))
				.routeId(callbackRoute);
	}

	public RouteDefinition buildQueuedCallbackRoute(String coreName, String routeName) throws RepositoryException {
		String callbackRoute = routeName+"Callback";
		String resultRoute = routeName+"Response";

		from(direct(routeName))
			.routeId(routeName)
			.setProperty(HeaderKey.MessageRouterCallback, constant(direct(callbackRoute)))
			.to(direct(coreName))
			.setHeader(QueueReader.DESTINATION_ROUTE, constant(direct(resultRoute)))
			.to(rabbitQueue());

		from(direct(resultRoute))
			.routeId(resultRoute)
			.removeHeaders("camel*")	// Needed to prevent overriding routingKey in camel endpoint
			.recipientList(simple("${header.response_uri}"));


		return from(direct(callbackRoute))
				.routeId(callbackRoute);
	}

    public abstract void configureRoute() throws Exception;

	protected String rabbitQueue() throws RepositoryException {
		String contextName = getContext().getName();
		RabbitConfig rabbitConfig = RabbitConfigRepository.getInstance().getByChannelName(contextName);
		final String RMQ_EXCHANGE = rabbitConfig.getUri() + "/Camel";
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&"+rabbitConfig.getUsernamePassword();
		final String RMQ_ROUTING = "&queue=m." + contextName + "&routingKey=m." + contextName;

		return "rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS + RMQ_ROUTING;
	}
}
