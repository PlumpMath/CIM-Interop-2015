package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
		IDataManager dataManager = (IDataManager)exchange.getIn().getBody();
		String identifier = (String)exchange.getIn().getHeader("identifier");

		String traceResult = null;
		if (identifier != null) {
			String nhsNumber = identifier.substring(4);
			traceResult = dataManager.tracePatientByNhsNumber(nhsNumber);
		} else {
			String surname = (String)exchange.getIn().getHeader("surname");
			String dob = (String)exchange.getIn().getHeader("dob");
			Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
			String gender = (String)exchange.getIn().getHeader("gender");
			String forenames = (String)exchange.getIn().getHeader("forename");
			String postcode = (String)exchange.getIn().getHeader("postcode");

			traceResult = dataManager.tracePatientByDemographics(surname, dateOfBirth, gender, forenames, postcode);
		}

		exchange.getOut().setBody(traceResult);
    }
}
