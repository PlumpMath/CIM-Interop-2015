package org.endeavourhealth.cim.processor.orders;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Order;

public class RequestOrderProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		String odsCode = (String) exchange.getIn().getHeader("odsCode");
		String orderRequestData = (String) exchange.getIn().getBody();	// FHIR format
		Order orderRequest = (Order)new JsonParser().parse(orderRequestData);

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		// Get data transformer for service
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		//String orderRequestInNativeFormat = transformer.fromFHIROrder(orderRequest);

		// Get patient data by NHS Number
		//dataAdapter.requestOrder(odsCode, orderRequestInNativeFormat);
	}
}
