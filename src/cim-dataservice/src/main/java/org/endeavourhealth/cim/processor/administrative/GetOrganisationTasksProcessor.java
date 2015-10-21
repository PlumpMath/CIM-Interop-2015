package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.common.core.HeaderKey;

public class GetOrganisationTasksProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getOrganisationTasks(odsCode);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
