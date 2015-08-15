package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetSlotsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
		String scheduleId = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Schedule);
		DateSearchParameter dateSearchParameter = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, HeaderKey.Date));

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getSlots(odsCode, scheduleId);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
