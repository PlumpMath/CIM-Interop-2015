package org.endeavourhealth.cim.camel.processors.demographics;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

import java.util.Date;

public class GetAllPatientsProcessor implements Processor {

	public GetAllPatientsProcessor() {
	}

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		Date lastUpdated = null;

		if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.LastUpdated))
			lastUpdated = ExchangeHelper.getInHeaderDate(exchange, CIMHeaderKey.LastUpdated);

		DataManager dataManager = new DataManager();
		String requestBody = dataManager.getChangedPatients(odsCode, lastUpdated);

		ExchangeHelper.setInBodyString(exchange, requestBody);
	}
}
