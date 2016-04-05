package org.endeavourhealth.cim.camel.processors.tasks;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

import java.util.UUID;

public class GetPatientTasksProcessor implements org.apache.camel.Processor
{
	@Override
	public void process(Exchange exchange) throws Exception
	{
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getPatientTasks(odsCode, patientId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
