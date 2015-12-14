package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.async.processor.*;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BulkPatientRoute extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "BulkPatient";

	@Override
	public void configureRoute() throws Exception {
		buildWrappedRoute(RouteWrapper.ROUTE_NAME, ROUTE_NAME)
			.process(new CacheFullRecord())
			.split(header("protocols"))
				.process(new ApplyInformationSharingProtocolFilters())
				.process(new GetProtocolSubscribers())
				.recipientList(header("subscribers"))
			.end();
	}
}
