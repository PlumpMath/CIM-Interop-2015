package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;

import java.util.UUID;

public class GetPatientRecordByPatientId implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String patientId = (String)exchange.getIn().getHeader("id");
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        // Get patientApi data (native format) using adapter
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);
        String patientData = dataAdapter.getPatientRecordByPatientId(UUID.fromString(patientId));

        // Get patientApi data transformer for service (native format -> FHIR)
        Transformer transformer = TransformerFactory.getTransformerForService(odsCode);
        Bundle bundle = transformer.toFHIRBundle(patientData);
        String body = new JsonParser().composeString(bundle);

        exchange.getIn().setBody(body);
    }
}
