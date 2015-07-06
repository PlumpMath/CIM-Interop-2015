package org.endeavourhealth.cim.processor.orders;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class RequestOrderProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		String odsCode = (String) exchange.getIn().getHeader("odsCode");
		String orderRequestData = (String) exchange.getIn().getBody();	// FHIR format

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		dataManager.requestOrder(odsCode, orderRequestData);
	}
}
