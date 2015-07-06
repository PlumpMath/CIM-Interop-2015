package org.endeavourhealth.cim.processor.event;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.UUID;

public class GetConditions implements Processor {
	public void process(Exchange exchange) throws Exception {
		String odsCode =(String) exchange.getIn().getHeader("odsCode");
		String patientId =(String) exchange.getIn().getHeader("id");

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getConditionsByPatientId(odsCode, UUID.fromString(patientId));

		exchange.getIn().setBody(body);


	}
}
