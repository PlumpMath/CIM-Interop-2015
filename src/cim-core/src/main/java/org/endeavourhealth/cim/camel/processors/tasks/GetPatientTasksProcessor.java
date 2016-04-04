package org.endeavourhealth.cim.camel.processors.tasks;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

public class GetPatientTasksProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		DataManager dataManager = new DataManager();
		String responseBody = dataManager.getPatientTasks(odsCode, patientId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
