package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.endeavourhealth.cim.TestRegistry;
import org.endeavourhealth.cim.adapter.AdapterFactory;
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
			public void configure() {
				AdapterFactory.setRegistry(new TestRegistry());

				from("direct:start")
					.process(new GetSchedulesProcessor())
					.to("mock:result");
			}
		};
	}

	@Test(expected = CamelExecutionException.class)
	public void testProcessNoParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		headerParams.put("date", null);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test(expected = CamelExecutionException.class)
	public void testProcessBothParams() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", "practitioner|G12345");
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test(expected = CamelExecutionException.class)
	public void testProcessWrongDateType() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		headerParams.put("date", "2015-06-22T00:00:00Z");

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test(expected = CamelExecutionException.class)
	public void testProcessNotEnoughDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		headerParams.put("date", dates);

		template.sendBodyAndHeaders(null, headerParams);

		resultEndpoint.assertIsSatisfied();
	}

	@Test(expected = CamelExecutionException.class)
	public void testProcessToManyDates() throws Exception {
		Map<String, Object> headerParams = new HashMap<>();
		headerParams.put("odsCode", "A99999");
		headerParams.put("actor", null);
		ArrayList<String> dates = new ArrayList<>();
		dates.add("2015-06-22T00:00:00Z");
		dates.add("2015-06-25T00:00:00Z");
		dates.add("2015-06-30T00:00:00Z");
		headerParams.put("date", dates);

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
