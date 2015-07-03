package org.endeavourhealth.cim.endpoints;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.processor.schedules.GetSchedulesProcessor;
import org.endeavourhealth.cim.routes.endpoints.ScheduleEndpoint;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchedulesTest extends CamelTestSupport {
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() throws Exception {
				getContext().setTracing(true);

				Registry.setInstance(new TestRegistry());

				this.includeRoutes(new ScheduleEndpoint());

				from("direct:start")
					.to("direct:GetSchedules")
					.to("mock:result");
			}
		};
	}

	@Test
	public void NoParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		headerParams.put("date", null);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(GetSchedulesProcessor.EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void BothParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", "practitioner|G12345");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(GetSchedulesProcessor.EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void WrongDateType() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		headerParams.put("date", "2015-06-22T00:00:00Z");

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(GetSchedulesProcessor.EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void NotEnoughDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void TooManyDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-25T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_ONE_OR_TWO_ELEMENTS);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void TwoDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}
}
