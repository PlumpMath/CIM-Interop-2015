package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.TransformerBase;
import org.endeavourhealth.cim.transform.TransformerFactory;

public class GetPatientByIdentifier implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        // Get data from exchange
        String nhsNumber = (String)exchange.getIn().getHeader("identifier");
        nhsNumber = nhsNumber.substring(4);
        String odsCode = (String) exchange.getIn().getHeader("odsCode");

        // Get the relevant data adapter from the factory
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

        // Get patient data by NHS Number
        String patientDataInNativeFormat = dataAdapter.getPatientDemographicsByNHSNumber(nhsNumber);

        // Get the relevant transformer from the factory
        TransformerBase transformer = TransformerFactory.getTransformerForService(odsCode);

        // Set the message body to the transformed (FHIR) version of the data
        exchange.getIn().setBody(transformer.toFHIRCareRecord(patientDataInNativeFormat));
    }
}
