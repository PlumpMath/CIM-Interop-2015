package org.endeavourhealth.cim.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.processor.core.DataProtocolProcessor;
import org.endeavourhealth.cim.routes.builders.CIMDataProtocol;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CIMDataProtocolTest extends CamelTestSupport {
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@EndpointInject(uri = "mock:error")
	protected MockEndpoint errorEndpoint;

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() throws Exception {
				Registry.setInstance(new TestRegistry());

				getContext().setTracing(true);

				this.includeRoutes(new CIMDataProtocol());

				from("direct:start")
					.to("direct:CIMDataProtocol")
					.to("mock:error");

				from("direct:CIMDataProtocolResult")
					.to("mock:result");
			}

		};
	}

	@Test
	public void testProcessInvalidOrgNoRelationship() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "invalidOrg");
		headerParams.put("odsCode", "Z99999");

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_FORBIDDEN);
		errorEndpoint.expectedBodiesReceived(DataProtocolProcessor.NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM);

		template.sendBodyAndHeaders(null, headerParams);

		errorEndpoint.assertIsSatisfied();
	}


	@Test
	public void testProcessLegitOrgNoRelationship() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "Z99999");

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_FORBIDDEN);
		errorEndpoint.expectedBodiesReceived(DataProtocolProcessor.SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);

		template.sendBodyAndHeaders(null, headerParams);

		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void testProcessSuccess() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "A99999");

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

}
