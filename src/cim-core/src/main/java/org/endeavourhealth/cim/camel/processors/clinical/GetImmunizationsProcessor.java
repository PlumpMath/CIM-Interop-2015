package org.endeavourhealth.cim.camel.processors.clinical;

import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.exceptions.NotFoundException;
import org.endeavourhealth.core.utils.TextUtils;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;

public class GetImmunizationsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
		String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getImmunizations(odsCode, patientId);

		if (TextUtils.isNullOrTrimmedEmpty(responseBody))
			throw new NotFoundException("patient");

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
