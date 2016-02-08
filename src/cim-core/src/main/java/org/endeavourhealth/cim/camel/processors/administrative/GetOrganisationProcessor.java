package org.endeavourhealth.cim.camel.processors.administrative;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

public class GetOrganisationProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Identifier, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getOrganisationByOdsCode(odsCode);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
