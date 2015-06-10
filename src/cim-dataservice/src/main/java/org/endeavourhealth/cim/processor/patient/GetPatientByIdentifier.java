package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Patient;

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

        // Get patientApi data transformer for service (native format -> FHIR)
        Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
        Patient patient = transformer.toFHIRPatient(patientDataInNativeFormat);
        String body = new JsonParser().composeString(patient);

        exchange.getIn().setBody(body);
    }
}
