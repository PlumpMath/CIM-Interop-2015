package org.endeavourhealth.cim.endpoints;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.cim.processor.administrative.GetSchedulesProcessor;
import org.endeavourhealth.common.core.exceptions.InvalidParamException;
import org.endeavourhealth.common.core.exceptions.MissingParamException;
import org.endeavourhealth.common.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchedulesTest extends CamelTestSupport {
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
				ISFManager.setInstance(new org.endeavourhealth.cim.InformationSharingFrameworkCaseRename.TestISFManager());

				onException(Exception.class)
					.to("mock:error")
					.handled(true);

				from("direct:start")
					.process(new GetSchedulesProcessor())
					.to("mock:result");
			}
		};
	}

	@Test
	public void NoParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(MissingParamException.class, exception);
	}

	@Test
	public void BothParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("practitioner", "G12345");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void SingleDate() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("date", "2015-06-22T00:00:00Z");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();

	}

	@Test
	public void NotEnoughDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		ArrayList<String> dates = new ArrayList<>();
		headerParams.put("date", dates);

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void TooManyDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-25T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
		Exception exception = (Exception)errorEndpoint.getReceivedExchanges().get(0).getProperty("CamelExceptionCaught");
		assertIsInstanceOf(InvalidParamException.class, exception);
	}

	@Test
	public void TwoDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}

	@Test
	public void Practitioner() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor:Practitioner", "2190964b-2d34-4eaa-8c60-9fbc086748a9");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}}
