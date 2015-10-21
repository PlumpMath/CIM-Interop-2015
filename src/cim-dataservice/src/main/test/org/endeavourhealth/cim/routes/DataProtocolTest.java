package org.endeavourhealth.cim.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.common.processor.DataProtocolProcessor;
import org.endeavourhealth.common.routes.core.DataProtocol;
import org.endeavourhealth.cim.routes.config.Configuration;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DataProtocolTest extends CamelTestSupport {
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
				ISFManager.setInstance(new org.endeavourhealth.cim.informationSharingFramework.TestISFManager());

				this.includeRoutes(new Configuration());
				this.includeRoutes(new DataProtocol());

				from("direct:start")
					.to("direct:CIMDataProtocol");

				from("direct:CIMInvalidMessage")
					.to("mock:error")
					.stop();

				from("direct:CIMDataProtocolResult")
					.to("mock:result")
					.stop();
			}

		};
	}

	@Test
	public void InvalidOrg() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "invalidOrg");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_FORBIDDEN);
		errorEndpoint.expectedBodiesReceived(DataProtocolProcessor.SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}


	@Test
	public void LegitOrgNoRelationship() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_FORBIDDEN);
		errorEndpoint.expectedBodiesReceived(DataProtocolProcessor.SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void LegitOrgWithRelationship() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "A99999");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

}
