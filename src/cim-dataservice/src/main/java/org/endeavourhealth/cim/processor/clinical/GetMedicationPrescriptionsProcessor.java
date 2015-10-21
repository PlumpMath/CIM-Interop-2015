package org.endeavourhealth.cim.processor.clinical;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.NotFoundException;
import org.endeavourhealth.core.text.TextUtils;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetMedicationPrescriptionsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
		String patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Id, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getMedicationPrescriptions(odsCode, patientId);

		if (TextUtils.isNullOrTrimmedEmpty(responseBody))
			throw new NotFoundException("patient");

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
