package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;

public class GetPatientTasksProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
		String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getPatientTasks(odsCode, patientId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
