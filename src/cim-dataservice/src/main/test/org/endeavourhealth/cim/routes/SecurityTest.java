package org.endeavourhealth.cim.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.cim.TestUserRepository;
import org.endeavourhealth.common.core.exceptions.SecurityFailedException;
import org.endeavourhealth.common.processor.SecurityProcessor;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.informationSharingFramework.TestISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.core.repository.user.data.UserRepository;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SecurityTest extends CamelTestSupport {
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
				UserRepository.setInstance(new TestUserRepository());

				onException(Exception.class)
					.to("mock:error")
					.handled(true);

				from("direct:start")
					.process(new SecurityProcessor())
					.to("mock:result");
			}
		};
	}

	@Test
	public void InvalidApiKey() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("CamelHttpPath", "http://known.uri/path/resource");
		headerParams.put("CamelHttpQuery", "param1=value1&param2=value2");
		headerParams.put("api_key", "null");
		headerParams.put("odsCode", "Z99999");
		headerParams.put("hash","4AcTI+rE3HZmqUTL+7IyRJ/upMJtcjyNviLYlmf3l2U=");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(SecurityFailedException.class, exception);
	}

	@Test
	public void MissingHash() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("CamelHttpPath", "http://known.uri/path/resource");
		headerParams.put("CamelHttpQuery", "param1=value1&param2=value2");
		headerParams.put("api_key", "foobar");
		headerParams.put("odsCode", "Z99999");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(SecurityFailedException.class, exception);
	}

	@Test
	public void IncorrectHash() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("CamelHttpPath", "http://known.uri/path/resource");
		headerParams.put("CamelHttpQuery", "param1=value1&param2=value2");
		headerParams.put("api_key", "foobar");
		headerParams.put("odsCode", "Z99999");
		headerParams.put("hash","4AcTI+rE3HZmqUTL+7IyRJ/upMJtcjyNviLYlmf3l2U=");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(SecurityFailedException.class, exception);
	}

	@Test
	public void CorrectHashInvalidApiKey() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("CamelHttpPath", "http://known.uri/path/resource");
		headerParams.put("CamelHttpQuery", "param1=value1&param2=value2");
		headerParams.put("api_key", "foobar");
		headerParams.put("odsCode", "A99999");
		headerParams.put("hash","4AcTI+rE3HZmqUTL+7IyRJ/upMJtcjyNviLYlmf3l2U=");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(SecurityFailedException.class, exception);
	}
}
