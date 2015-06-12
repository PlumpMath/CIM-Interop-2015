package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TracePersonProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        IDataAdapter dataAdapter = (IDataAdapter)exchange.getIn().getBody();
		String identifier = (String)exchange.getIn().getHeader("identifier");

		String traceResult = null;
		if (identifier != null) {
			String nhsNumber = identifier.substring(4);
			traceResult = dataAdapter.tracePatientByNhsNumber(nhsNumber);
		} else {
			String surname = (String)exchange.getIn().getHeader("surname");
			String dob = (String)exchange.getIn().getHeader("dob");
			Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
			String gender = (String)exchange.getIn().getHeader("gender");
			String forenames = (String)exchange.getIn().getHeader("forename");
			String postcode = (String)exchange.getIn().getHeader("postcode");

			traceResult = dataAdapter.tracePatientByDemographics(surname, dateOfBirth, gender, forenames, postcode);
		}

		Patient patient = null;

		if (traceResult != null && traceResult.isEmpty() == false) {
			Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
			patient = transformer.toFHIRPatient(traceResult);
		}

		exchange.getOut().setBody(patient);
    }
}
