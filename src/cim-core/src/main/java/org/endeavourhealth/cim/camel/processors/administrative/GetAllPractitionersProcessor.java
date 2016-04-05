package org.endeavourhealth.cim.camel.processors.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetAllPractitionersProcessor implements Processor
{
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception
	{
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getAllPractitioners(odsCode);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
