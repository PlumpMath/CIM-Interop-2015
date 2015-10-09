package org.endeavourhealth.cim.endpoints;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.common.searchParameters.DateSearchParameter;
import org.endeavourhealth.cim.routes.config.Configuration;
import org.endeavourhealth.cim.routes.endpoints.rest.ScheduleEndpoints;
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
				ISFManager.setInstance(new org.endeavourhealth.cim.informationSharingFramework.TestISFManager());

				this.includeRoutes(new Configuration());
				this.includeRoutes(new ScheduleEndpoints());

				from("direct:start")
					.to("direct:GetSchedules")
					.to("mock:result");

				from("direct:CIMInvalidMessage")
					.to("mock:error")
					.stop();
			}
		};
	}

	@Test
	public void NoParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");

		resultEndpoint.expectedMessageCount(0);
		errorEndpoint.expectedMessageCount(1);

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		errorEndpoint.expectedBodiesReceived("");

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
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

		errorEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		errorEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
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
		headerParams.put("actor:Practitioner", "G12345");

		resultEndpoint.expectedMessageCount(1);
		errorEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
		errorEndpoint.assertIsSatisfied();
	}}
