package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = (IDataManager)exchange.getIn().getBody();
		String identifier = (String)exchange.getIn().getHeader(HeaderKey.Identifier);

		String traceResult = null;
		if (identifier != null) {
			String nhsNumber = identifier.substring(4);
			traceResult = dataManager.tracePatientByNhsNumber(nhsNumber);
		} else {
			String surname = (String)exchange.getIn().getHeader(HeaderKey.Surname);
			String dob = (String)exchange.getIn().getHeader(HeaderKey.DOB);
			Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
			String gender = (String)exchange.getIn().getHeader(HeaderKey.Gender);
			String forenames = (String)exchange.getIn().getHeader(HeaderKey.Forename);
			String postcode = (String)exchange.getIn().getHeader(HeaderKey.PostCode);

			traceResult = dataManager.tracePatientByDemographics(surname, dateOfBirth, gender, forenames, postcode);
		}

		exchange.getOut().setBody(traceResult);
    }
}
