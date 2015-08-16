package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonByDemographicsProcessor implements org.apache.camel.Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

		String surname = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Surname);
		Date dateOfBirth = ExchangeHelper.getInHeaderDate(exchange, HeaderKey.DOB);
		String gender = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Gender);
		String forenames = ExchangeHelper.getInHeaderString(exchange, HeaderKey.Forename);
		String postcode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.PostCode);
		IDataManager dataManager = (IDataManager)ExchangeHelper.getInBodyObject(exchange);

		String responseBody = dataManager.tracePatientByDemographics(surname, dateOfBirth, gender, forenames, postcode);

		ExchangeHelper.setOutBodyString(exchange, responseBody);
    }
}
