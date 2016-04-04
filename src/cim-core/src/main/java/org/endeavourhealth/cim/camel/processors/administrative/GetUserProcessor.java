package org.endeavourhealth.cim.camel.processors.administrative;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

public class GetUserProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String userId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		DataManager dataManager = new DataManager();
		String responseBody = dataManager.getUser(odsCode, userId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
