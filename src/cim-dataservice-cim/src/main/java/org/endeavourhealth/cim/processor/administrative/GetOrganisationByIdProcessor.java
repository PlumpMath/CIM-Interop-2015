package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;

public class GetOrganisationByIdProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String organisationId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getAllDataAdapters().get(0);
		String responseBody = dataManager.getOrganisationById(organisationId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
