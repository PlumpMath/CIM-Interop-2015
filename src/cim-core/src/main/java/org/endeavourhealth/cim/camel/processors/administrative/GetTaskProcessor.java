package org.endeavourhealth.cim.camel.processors.administrative;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

public class GetTaskProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String taskId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getTask(odsCode, taskId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
