package org.endeavourhealth.cim.camel.processors.tasks;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

public class AddTaskProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String taskData = ExchangeHelper.getInBodyString(exchange);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		dataManager.addTask(odsCode, taskData);

		// ExchangeHelper.setInBodyString(exchange, responseBody);

	}
}
