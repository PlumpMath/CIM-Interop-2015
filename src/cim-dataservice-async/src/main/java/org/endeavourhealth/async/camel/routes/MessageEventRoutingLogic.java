package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MessageEventRoutingLogic extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "ProcessMessageRoutingLogic";
	public static final String MESSAGE_EVENT = "MessageEvent";

	@Override
	public void configureRoute() throws Exception {
		from (direct(ROUTE_NAME))
			.routeId(ROUTE_NAME)
			.to(direct(TransformToFhir.ROUTE_NAME))
			.choice()
				.when(header(MESSAGE_EVENT).isEqualTo("bulkPatient"))
					.to(direct(BulkPatientRoute.ROUTE_NAME))
				.otherwise()
					.throwException(new SourceDocumentInvalidException("Message event not known"))
			.end();
	}
}
