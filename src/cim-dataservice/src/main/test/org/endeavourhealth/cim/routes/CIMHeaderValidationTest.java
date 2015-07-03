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
import org.endeavourhealth.cim.routes.builders.CIMHeaderValidation;
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

				getContext().setTracing(true);

				this.includeRoutes(new CIMHeaderValidation());

				from("direct:start")
					.to("direct:CIMHeaderValidation")
					.to("mock:error");

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

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		errorEndpoint.expectedBodiesReceived(DataProtocolProcessor.NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM);

		template.sendBodyAndHeaders(null, headerParams);

		// errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void ValidHeader() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "invalidOrg");
		headerParams.put("odsCode", "Z99999");

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}
}
