package org.endeavourhealth.cim.camel.processors.administrative;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;

public class GetOrganisationProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String organisationId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getAllDataAdapters().get(0);
		String responseBody = dataManager.getOrganisationById(organisationId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
