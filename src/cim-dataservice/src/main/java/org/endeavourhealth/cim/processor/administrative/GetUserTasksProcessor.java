package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetUserTasksProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
		String userId = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getUserTasks(odsCode, userId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
