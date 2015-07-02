package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.ArrayParameter;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;

import java.util.ArrayList;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public static final String EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED = "Either an actor, a date range or both must be supplied.";

	public void process(Exchange exchange) throws Exception {

		String odsCode = (String)exchange.getIn().getHeader("odsCode");
		DateSearchParameter date = DateSearchParameter.Parse(ArrayParameter.Receive(exchange.getIn().getHeader("date")));
		String actor = (String)exchange.getIn().getHeader("actor");

		if ((actor == null && date == null) || (actor != null && date != null) || (date != null) && (date.getIntervalStart().equals(date.getIntervalEnd())))
			throw new IllegalArgumentException(EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		String schedulesInNativeFormat;
		if (date != null) {
			schedulesInNativeFormat = GetSchedulesByDateRange(odsCode, date, dataAdapter);
		} else
			schedulesInNativeFormat = GetSchedulesByActor(odsCode, actor, dataAdapter);

		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);

		Bundle bundle = transformer.toFHIRScheduleBundle(schedulesInNativeFormat);
		String body = new JsonParser().composeString(bundle);

		exchange.getIn().setBody(body);
	}

	private String GetSchedulesByDateRange(String odsCode, DateSearchParameter date, IDataAdapter dataAdapter) throws Exception {
		return dataAdapter.getSchedules(odsCode, date.getIntervalStart(), date.getIntervalEnd());
	}

	private String GetSchedulesByActor(String odsCode, String actor, IDataAdapter dataAdapter) {
		return "";
	}
}
