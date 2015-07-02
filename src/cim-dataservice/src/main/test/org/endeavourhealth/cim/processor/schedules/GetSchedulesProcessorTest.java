package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.routes.endpoints.ScheduleEndpoint;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetSchedulesProcessorTest extends CamelTestSupport {
	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() throws Exception {
				AdapterFactory.setRegistry(new TestRegistry());

				this.includeRoutes(new ScheduleEndpoint());

				from("direct:start")
					.to("direct:GetSchedules")
					.to("mock:result");
			}
		};
	}

	@Test
	public void testProcessNoParams() throws Exception {
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
	public void testProcessBothParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", "practitioner|G12345");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_TWO_ELEMENTS_ONLY);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void testProcessWrongDateType() throws Exception {
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
	public void testProcessNotEnoughDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_TWO_ELEMENTS_ONLY);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void testProcessToManyDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-25T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		resultEndpoint.expectedHeaderReceived("CamelHttpResponseCode", HttpStatus.SC_BAD_REQUEST);
		resultEndpoint.expectedBodiesReceived(DateSearchParameter.DATE_TIMES_MUST_CONTAIN_TWO_ELEMENTS_ONLY);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void testProcessSuccess() throws Exception {
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