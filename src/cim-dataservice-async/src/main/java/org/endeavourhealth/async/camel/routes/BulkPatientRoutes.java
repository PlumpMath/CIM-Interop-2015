package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.async.processor.*;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BulkPatientRoutes extends BaseRouteBuilder {
	public static final String POST_BULK_PATIENT_ROUTE = "PostBulkPatient";

	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "/Bulk/Patient";

		buildQueuedCallbackRoute(AsyncCore.ROUTE_NAME, POST_BULK_PATIENT_ROUTE)
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
