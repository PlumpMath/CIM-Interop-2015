package org.endeavourhealth.cim.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.cim.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.informationSharingFramework.TestISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.routes.builders.CIMHeaderValidation;
import org.endeavourhealth.cim.routes.config.RestConfiguration;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CIMHeaderValidationTest extends CamelTestSupport {
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
				this.includeRoutes(new CIMHeaderValidation());

				from("direct:start")
					.to("direct:CIMHeaderValidation");

				from("direct:CIMInvalidMessage")
						.to("mock:error")
						.stop();

				from("direct:CIMHeaderValidationResult")
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
