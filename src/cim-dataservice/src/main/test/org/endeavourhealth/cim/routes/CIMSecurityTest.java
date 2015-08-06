package org.endeavourhealth.cim.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.informationSharingFramework.TestISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.processor.core.SecurityProcessor;
import org.endeavourhealth.cim.routes.builders.CIMSecurity;
import org.endeavourhealth.cim.routes.config.RestConfiguration;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CIMSecurityTest extends CamelTestSupport {
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
				ISFManager.setInstance(new TestISFManager());

				this.includeRoutes(new RestConfiguration());
				this.includeRoutes(new CIMSecurity());

				from("direct:start")
					.to("direct:CIMSecurity");

				from("direct:CIMInvalidMessage")
					.to("mock:error")
					.stop();

				from("direct:CIMSecurityResult")
					.to("mock:result")
					.stop();
			}
		};
	}

	@Test
	public void InvalidApiKey() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "null");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(0);

		errorEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_UNAUTHORIZED);
		errorEndpoint.expectedBodiesReceived(SecurityProcessor.INVALID_SESSION);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void IncorrectHash() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "foobar");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(0);

		errorEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_UNAUTHORIZED);
		errorEndpoint.expectedBodiesReceived(SecurityProcessor.INVALID_SESSION);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void SwaggerApiKey() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "A99999");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void CorrectHash() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "foobar");
		headerParams.put("odsCode", "A99999");
		headerParams.put("hash","K5DOPZBbuiJrPQGHVwcbKoOX2OQtnT27lpyWrYRV3bo=");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}
}
