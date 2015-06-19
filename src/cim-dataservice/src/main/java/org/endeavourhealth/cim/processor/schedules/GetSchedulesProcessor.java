package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.model.Bundle;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		String odsCode = (String) exchange.getIn().getHeader("odsCode");

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		// Get patient data by NHS Number
		String schedulesInNativeFormat = dataAdapter.getSchedules(odsCode);

		// Get data transformer for service
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		Bundle schedules = (Bundle)transformer.toFHIRBundle(schedulesInNativeFormat);
	}
}
