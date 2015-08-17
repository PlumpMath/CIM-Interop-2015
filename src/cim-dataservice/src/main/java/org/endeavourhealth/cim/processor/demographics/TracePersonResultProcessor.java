package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.Patient;

import java.util.ArrayList;

public class TracePersonResultProcessor implements org.apache.camel.Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        //ArrayList<Patient> patients = (ArrayList<Patient>)exchange.getIn().getBody();

        // Create bundle of patients from returned list
    }
}
