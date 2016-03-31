package org.endeavourhealth.cim.camel.processors.tasks;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

public class GetOrganisationTasksProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getOrganisationTasks(odsCode);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
