package org.endeavourhealth.cim.processor.slots;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetSlotsProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		String odsCode = (String) exchange.getIn().getHeader("odsCode");
		String scheduleId =(String)exchange.getIn().getHeader("schedule");
		Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse((String)exchange.getIn().getHeader("start"));

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		// Get patient data by NHS Number
		String schedulesInNativeFormat = dataAdapter.getSlots(odsCode, scheduleId, startTime);

		// Get data transformer for service
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		// Bundle schedules = (Bundle)transformer.toFHIRBundle(schedulesInNativeFormat);
		exchange.getIn().setBody(schedulesInNativeFormat);
	}
}
