package org.endeavourhealth.async.camel.endpoints;

import org.endeavourhealth.async.camel.routes.AsyncPreQueue;
import org.endeavourhealth.async.camel.routes.BulkPatientRoutes;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.HttpVerb;

public class BulkPatientEndpoints extends BaseRouteBuilder{
	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "/Bulk/Patient";

		rest(BASE_PATH)

		.post("")
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to(direct(AsyncPreQueue.ROUTE_NAME))
			.to(queueWriter(BulkPatientRoutes.POST_BULK_PATIENT_ROUTE))
		.endRest();
	}
}
