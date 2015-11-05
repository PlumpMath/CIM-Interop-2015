package org.endeavourhealth.async.routes;

import org.endeavourhealth.async.processor.*;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BulkPatient extends BaseRouteBuilder {
	public static final String POST_BULK_PATIENT_ROUTE = "PostBulkPatient";

	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "/Bulk/Patient";

		buildRabbitCallbackRoute(BASE_PATH, HttpVerb.POST, POST_BULK_PATIENT_ROUTE)
			.process(new TransformationToFhir())
			.process(new CacheFullRecord())
			.process(new LoadDataDistributionProtocols())
			.split(header("protocols"))
				.process(new ApplyDataDistributionProtocol())
				.process(new GetProtocolSubscribers())
				.recipientList(header("subscribers"))
				.end();
	}
}
