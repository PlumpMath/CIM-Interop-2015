package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;

public class GetSlotsProcessor extends CIMProcessor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);
		String scheduleId = getInHeaderString(exchange, HeaderKey.Schedule);
		DateSearchParameter dateSearchParameter = DateSearchParameter.Parse(getInHeaderArray(exchange, HeaderKey.Date));

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getSlots(odsCode, scheduleId);

		setInBodyString(exchange, body);
	}
}
