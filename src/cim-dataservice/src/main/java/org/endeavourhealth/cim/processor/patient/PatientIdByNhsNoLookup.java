package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;

import java.util.UUID;

public class PatientIdByNhsNoLookup implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
//        String nhsNumber = (String)exchange.getIn().getHeader("nhsNo");
//        String serviceId = (String)exchange.getIn().getHeader("serviceId");
//        exchange.getIn().setBody(String.format("Looked up patient {%s} at service {%s}%n", nhsNumber, serviceId));
        exchange.getIn().setHeader("patientId", UUID.randomUUID().toString());
    }
}
