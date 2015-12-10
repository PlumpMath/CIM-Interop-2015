package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.async.processor.*;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ProcessMessageRoute extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "ProcessMessage";

	@Override
	public void configureRoute() throws Exception {
		// buildWrappedRouteWithQueuedResponse(AsyncCore.ROUTE_NAME, ROUTE_NAME)
		buildWrappedRoute(RouteWrapper.ROUTE_NAME, ROUTE_NAME)
			.process(new TransformationToFhir())
			.process(new CacheFullRecord())
			.process(new LoadInformationSharingProtocols())
			.split(header("protocols"))
				.process(new ApplyInformationSharingProtocol())
				.process(new GetProtocolSubscribers())
				.recipientList(header("subscribers"))
			.end();
	}
}
