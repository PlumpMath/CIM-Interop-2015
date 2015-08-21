package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetSlotsProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode, true);
		String scheduleId = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Schedule, true);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getSlots(odsCode, scheduleId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
