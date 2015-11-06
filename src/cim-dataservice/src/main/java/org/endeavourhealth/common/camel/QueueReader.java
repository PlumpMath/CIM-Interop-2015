package org.endeavourhealth.common.camel;

import org.endeavourhealth.common.core.BaseRouteBuilder;

public class QueueReader extends BaseRouteBuilder {
	public static final String DESTINATION_ROUTE = "DestinationRoute";
	@Override
	public void configureRoute() throws Exception {
		from(rabbitQueue())
			.routeId("QueueReader")
			.removeHeaders("rabbitmq.*")	// Needed to prevent overriding routingKey in camel endpoint
			.convertBodyTo(String.class)
			.recipientList(simple("${header."+DESTINATION_ROUTE+"}"));
	}
}
