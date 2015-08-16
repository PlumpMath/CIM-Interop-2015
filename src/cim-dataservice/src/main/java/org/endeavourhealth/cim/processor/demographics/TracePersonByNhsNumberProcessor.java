package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonByNhsNumberProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

		String identifier = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Identifier);
		IDataManager dataManager = (IDataManager)ExchangeHelper.getInBodyObject(exchange);

		String nhsNumber = identifier.substring(4);
		String responseBody = dataManager.tracePatientByNhsNumber(nhsNumber);

		ExchangeHelper.setOutBodyString(exchange, responseBody);
    }
}
