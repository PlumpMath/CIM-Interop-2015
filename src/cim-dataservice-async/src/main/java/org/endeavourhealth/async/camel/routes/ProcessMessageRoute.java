package org.endeavourhealth.async.camel.routes;

import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.PropertyKey;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ProcessMessageRoute extends BaseRouteBuilder {
	public static final String ROUTE_NAME = "ProcessMessage";
	public static final String SENDING_ORGANISATION = PropertyKey.SourceOdsCode;
	public static final String MESSAGE_EVENT = MessageEventRoutingLogic.MESSAGE_EVENT;
	public static final String CONTENT_TYPE = TransformToFhir.CONTENT_TYPE;
	public static final String ASYNC = "async";

	@Override
	public void configureRoute() throws Exception {
		from(direct(ROUTE_NAME))
			.routeId(ROUTE_NAME)
			.wireTap(direct(ComponentRouteName.AUDIT))
				.setProperty(PropertyKey.TapLocation, constant("Inbound"))
			.end()
			// .to(direct(ComponentRouteName.SECURITY)) api_key based - needs replacing?
			.choice()
				.when(simple("${header."+SENDING_ORGANISATION+"} == null || ${header."+SENDING_ORGANISATION+"} == \"\""))
					.throwException(new SourceDocumentInvalidException("Sending organisation not specified"))
				.when(simple("${header."+MESSAGE_EVENT+"} == null || ${header."+MESSAGE_EVENT+"} == \"\""))
					.throwException(new SourceDocumentInvalidException("Message event not specified"))
				.when(simple("${header."+CONTENT_TYPE+"} == null || ${header."+CONTENT_TYPE+"} == \"\""))
					.throwException(new SourceDocumentInvalidException("Content type not specified"))
				.otherwise()
					.choice()
						.when(header(ASYNC).isEqualTo("true"))
							.to(queued(MessageEventRoutingLogic.ROUTE_NAME, header(SENDING_ORGANISATION)))
						.otherwise()
							.to(direct(MessageEventRoutingLogic.ROUTE_NAME))
					.end()
			.end();
	}
}
