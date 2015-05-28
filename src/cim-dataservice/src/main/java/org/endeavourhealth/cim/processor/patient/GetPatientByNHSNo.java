package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.IDataAdapter;
import org.endeavourhealth.cim.common.ITransformer;
import org.endeavourhealth.cim.transformer.TransformerFactory;

import java.util.UUID;

public class GetPatientByNHSNo implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        // Get data from exchange
        String nhsNumber = (String)exchange.getIn().getHeader("nhsNo");
        int serviceId = Integer.parseInt((String) exchange.getIn().getHeader("serviceId"));

        // Get the relevant data adapter from the factory
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);

        // Get patient data by NHS Number
        String patientDataInNativeFormat = dataAdapter.getPatientByNHSNumber(nhsNumber);

        // Get the relevant transformer from the factory
        ITransformer transformer = TransformerFactory.getTransformerForService(serviceId);

        // Set the message body to the transformed (FHIR) version of the data
        exchange.getIn().setBody(transformer.toFHIR(patientDataInNativeFormat));
    }
}
