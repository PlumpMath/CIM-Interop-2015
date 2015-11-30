package org.endeavourhealth.common.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.common.TestRegistry;
import org.endeavourhealth.common.informationSharingFramework.TestISFManager;
import org.endeavourhealth.common.processor.HeaderValidationProcessor;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HeaderValidationTest extends CamelTestSupport {
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

				onException(Exception.class)
					.to("mock:error")
					.handled(true);

				from("direct:start")
					.process(new HeaderValidationProcessor())
					.to("mock:result");
			}

		};
	}

	@Test
	public void InvalidHeader() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "invalidOrg");
		headerParams.put("odsCode", "Z99999");

/*
		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		errorEndpoint.expectedBodiesReceived(HeaderValidationProcessor.INVALID_HEADER);
*/

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void ValidHeader() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "swagger");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}
}
