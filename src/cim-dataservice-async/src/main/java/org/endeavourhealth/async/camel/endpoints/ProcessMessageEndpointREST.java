package org.endeavourhealth.async.camel.endpoints;

import org.endeavourhealth.async.camel.routes.ProcessMessageRoute;
import org.endeavourhealth.common.core.BaseRouteBuilder;
import org.endeavourhealth.common.core.ComponentRouteName;
import org.endeavourhealth.common.core.HttpVerb;

public class ProcessMessageEndpointREST extends BaseRouteBuilder{
	@Override
	public void configureRoute() throws Exception {
		final String BASE_PATH = "$process-message";

		rest(BASE_PATH)

		.post("?async={async}&response-url={response-url}")
			.route()
			.routeId(HttpVerb.POST + BASE_PATH)
			.streamCaching()
			.to(direct(ComponentRouteName.FHIR_VALIDATION))
			.setHeader(
				ProcessMessageRoute.SENDING_ORGANISATION,
				xpath(
					"substring-after(/f:Bundle/f:entry/f:resource/f:MessageHeader/f:source/f:endpoint/@value,\"urn:x-fhir:uk:nhs:id:ODSOrganisationCode:\")",
					String.class)
						.namespace("f", "http://hl7.org/fhir"))
			.setHeader(ProcessMessageRoute.MESSAGE_EVENT, xpath("/f:Bundle/f:entry/f:resource/f:MessageHeader/f:event/f:code/@value", String.class)
					.namespace("f", "http://hl7.org/fhir"))
			.setHeader(ProcessMessageRoute.CONTENT_TYPE, xpath("/f:Bundle/f:entry/f:resource/f:Binary/f:contentType/@value", String.class)
					.namespace("f", "http://hl7.org/fhir"))
			// Async already set in calling path
			.setBody(xpath("/f:Bundle/f:entry/f:resource/f:Binary/f:content/@value", String.class).namespace("f", "http://hl7.org/fhir"))
			.unmarshal().base64()
			.to(direct(ProcessMessageRoute.ROUTE_NAME))
		.endRest();
	}
}
