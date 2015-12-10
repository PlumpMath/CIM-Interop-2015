package org.endeavourhealth.async.camel.endpoints;

import org.apache.camel.LoggingLevel;
import org.endeavourhealth.async.camel.routes.ProcessMessageRoute;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.HttpVerb;

public class ProcessMessageEndpoint extends BaseRouteBuilder{
	public static final String ROUTE_NAME = "ProcessMessageEndpoint";
	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "$process-message";

		rest(BASE_PATH)

		.post("?async={async}&response-url={response-url}")
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(direct(ProcessMessageEndpoint.ROUTE_NAME));

		buildWrappedRoute(EndpointWrapper.ROUTE_NAME, ProcessMessageEndpoint.ROUTE_NAME)
			.choice()
				.when(header("async").isEqualTo("true"))
					.to(queued(ProcessMessageRoute.ROUTE_NAME))
				.otherwise()
					.to(direct(ProcessMessageRoute.ROUTE_NAME))
				.end()
		.endRest();
	}
}
