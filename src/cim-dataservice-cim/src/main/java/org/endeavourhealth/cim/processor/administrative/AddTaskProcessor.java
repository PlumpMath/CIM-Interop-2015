package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;

public class AddTaskProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
		String taskData = ExchangeHelper.getInBodyString(exchange);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		dataManager.addTask(odsCode, taskData);

		// ExchangeHelper.setInBodyString(exchange, responseBody);

	}
}
