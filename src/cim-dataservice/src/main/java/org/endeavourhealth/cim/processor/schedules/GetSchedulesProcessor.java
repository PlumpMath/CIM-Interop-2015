package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;

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

		if (actor == null && date == null)
			throw new Exception("Invalid schedule criteria");

		if (date instanceof String ||
			date instanceof ArrayList && ((ArrayList)date).size() != 2)
			throw new Exception("Two dates must be supplied to specify a range");

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		ArrayList<Date> dateList = new ArrayList<>();
		for (String dateString : (ArrayList<String>)date)
			dateList.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString));

		Date dateFrom = Collections.min(dateList);
		Date dateTo = Collections.max(dateList);

		// Get schedules
		String schedulesInNativeFormat = dataAdapter.getSchedules(odsCode, dateFrom, dateTo);

		// Get data transformer for service
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		//Bundle schedules = (Bundle)transformer.toFHIRBundle(schedulesInNativeFormat);

		exchange.getIn().setBody(schedulesInNativeFormat);
	}
}
