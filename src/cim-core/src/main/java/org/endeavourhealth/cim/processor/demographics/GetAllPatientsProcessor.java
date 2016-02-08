package org.endeavourhealth.cim.processor.demographics;

import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;

import java.util.Date;

public class GetAllPatientsProcessor implements Processor {

	public GetAllPatientsProcessor() {
	}

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
		Date lastUpdated = null;

		if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.LastUpdated))
			lastUpdated = ExchangeHelper.getInHeaderDate(exchange, CIMHeaderKey.LastUpdated);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String requestBody = dataManager.getChangedPatients(odsCode, lastUpdated);

		ExchangeHelper.setInBodyString(exchange, requestBody);
	}
}
