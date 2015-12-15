package org.endeavourhealth.async.camel.endpoints;

import org.endeavourhealth.async.camel.routes.ProcessMessageRoute;
import org.endeavourhealth.async.processor.ProcessItkActionMessageEvent;
import org.endeavourhealth.common.core.BaseRouteBuilder;

public class ProcessMessageEndpointSOAP extends BaseRouteBuilder{
	@Override
	public void configureRoute() throws Exception {
//		from("mock:soap-endpoint")		// Just a mock endpoint to show theory
//			.routeId("SOAP-ENDPOINT")
//			.streamCaching()
//			.to("validator:distributionEnvelope-v2-0.xsd")
//			.setHeader(ProcessMessageRoute.SENDING_ORGANISATION, xpath("/soap:WhereIsThisDataElement", String.class))
//			.setHeader(ProcessMessageRoute.MESSAGE_EVENT, xpath("/soap:Envelope/soap:Header/wsa:Action", String.class))
//			.process(new ProcessItkActionMessageEvent())
//			.setHeader(ProcessMessageRoute.CONTENT_TYPE, constant("text/openhr"))	// - Assuming ITK is always OpenHR
//			.setHeader(ProcessMessageRoute.ASYNC, constant("true"))
//			.setBody(xpath("/soap:Envelope/soap:Body/itk:DistributionEnvelope/itk:payloads[0]", String.class))
//			//.to("validator:openHrSchema.xsd") - Assuming ITK is always OpenHR
//			.to(direct(ProcessMessageRoute.ROUTE_NAME))
//		.endRest();
	}
}
