package org.endeavourhealth.common.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.common.TestRegistry;
import org.endeavourhealth.common.core.exceptions.NoLegitimateRelationshipException;
import org.endeavourhealth.common.informationSharingFramework.TestISFManager;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.common.processor.DataProtocolProcessor;
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
				ISFManager.setInstance(new TestISFManager());

				onException(Exception.class)
						.to("mock:error")
						.handled(true);

				from("direct:start")
					.process(new DataProtocolProcessor())
					.to("mock:result");
			}

		};
	}

	@Test
	public void InvalidOrg() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("api_key", "invalidOrg");
		headerParams.put("odsCode", "INVALID_ODS_CODE");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(NoLegitimateRelationshipException.class, exception);
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
