package org.endeavourhealth.async.routes;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BulkPatient extends BaseRouteBuilder {
	public static final String POST_BULK_PATIENT_ROUTE = "PostBulkPatient";

	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "/Bulk/Patient";
		final String RMQ_EXCHANGE = Registry.Instance().getRabbitHost() + "/" + getContext().getName();
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&username=azureuser&password=Azureuser123&queue=" + POST_BULK_PATIENT_ROUTE;

		rest(BASE_PATH)

		// Rest to Rabbit writer
		.post()
				.route()
		.routeId(HttpVerb.POST + BASE_PATH)
		.to("rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS)
				.endRest();

		// Rabbit reader to route
		from("rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS)
		.to(Route.direct(POST_BULK_PATIENT_ROUTE));

		// Route
		buildCallbackRoute(POST_BULK_PATIENT_ROUTE)
				.to("log:Foo");
		// Transform
		// Cache record
		// Load DDP's
		// Split by ddp
			// Filter using ddp
			// Get ddp subscribers
			// Multicast subscribers (Rabbit)
	}
}
