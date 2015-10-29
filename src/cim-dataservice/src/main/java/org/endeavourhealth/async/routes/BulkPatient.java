package org.endeavourhealth.async.routes;

import org.endeavourhealth.async.processor.*;
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
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&"+Registry.Instance().getRabbitLogon()+"&queue=m." + POST_BULK_PATIENT_ROUTE;

		// Rest to Rabbit writer
		rest(BASE_PATH)
			.post()
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.to("rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS)
		.endRest();

		// Rabbit reader to route
		from("rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS)
			.convertBodyTo(String.class)
			.to(Route.direct(POST_BULK_PATIENT_ROUTE));

		// Route
		buildCallbackRoute(POST_BULK_PATIENT_ROUTE)
			.process(new TransformationToFhir())
			.process(new CacheFullRecord())
			.process(new LoadDataDistributionProtocols())
			.split(header("protocols"))
				.process(new ApplyDataDistributionProtocol())
				.process(new GetProtocolSubscribers())
				.recipientList(header("subscribers"));
	}
}
