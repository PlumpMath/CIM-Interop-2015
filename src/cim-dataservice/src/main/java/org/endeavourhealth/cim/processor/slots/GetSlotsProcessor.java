package org.endeavourhealth.cim.processor.slots;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ArrayListHelper;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetSlotsProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {

		String odsCode = (String) exchange.getIn().getHeader(HeaderKey.OdsCode);
		String scheduleId = (String)exchange.getIn().getHeader(HeaderKey.Schedule);
		DateSearchParameter dateSearchParameter = DateSearchParameter.Parse(ArrayListHelper.FromSingleOrArray(exchange.getIn().getHeader(HeaderKey.Start)));

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getSlots(odsCode, scheduleId);

		exchange.getIn().setBody(body);
	}
}
