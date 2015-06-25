package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;
import org.hl7.fhir.instance.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		String odsCode = (String) exchange.getIn().getHeader("odsCode");
		Object date = exchange.getIn().getHeader("date");
		String actor = (String)exchange.getIn().getHeader("actor");

		if ((actor == null && date == null) || (actor != null && date != null))
			throw new Exception("Either an actor OR a date range must be supplied.");

		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		String schedulesInNativeFormat;
		if (date != null) {
			if (!(date instanceof ArrayList))
				throw new Exception("Invalid date list");

			schedulesInNativeFormat = GetSchedulesByDateRange(odsCode, (ArrayList)date, dataAdapter);
		} else
			schedulesInNativeFormat = GetSchedulesByActor(odsCode, actor, dataAdapter);

		// Get data transformer for service
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);

		Bundle bundle = transformer.toFHIRScheduleBundle(schedulesInNativeFormat);
		String body = new JsonParser().composeString(bundle);

		exchange.getIn().setBody(body);
	}

	private String GetSchedulesByDateRange(String odsCode, ArrayList date, IDataAdapter dataAdapter) throws Exception {
		if (date.size() != 2)
			throw new Exception("Two dates must be supplied to specify a range");

		// Get the relevant data adapter from the factory
		ArrayList<Date> dateList = new ArrayList<>();
		for (String dateString :(ArrayList<String>)date)
			dateList.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString));

		Date dateFrom = Collections.min(dateList);
		Date dateTo = Collections.max(dateList);

		// Get schedules
		return dataAdapter.getSchedules(odsCode, dateFrom, dateTo);
	}

	private String GetSchedulesByActor(String odsCode, String actor, IDataAdapter dataAdapter) {
		return "";
	}
}
