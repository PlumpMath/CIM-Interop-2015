package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.TransformerBase;
import org.endeavourhealth.cim.transform.TransformerFactory;

import java.util.UUID;

public class GetPatientRecordByPatientId implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String patientId = (String)exchange.getIn().getHeader("patientId");
        String serviceId = (String) exchange.getIn().getHeader("serviceId");

        // Get patientApi data (native format) using adapter
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);
        String patientData = dataAdapter.getPatientRecordByPatientId(UUID.fromString(patientId));

        // Get patientApi data transformer for service (native format -> FHIR)
        TransformerBase transformer = TransformerFactory.getTransformerForService(serviceId);

        if (transformer == null)    // No transformation required
            return;

        // Transform from native format to FHIR
        String fhirPatientData = transformer.toFHIRCareRecord(patientData);

        String body = (String) exchange.getIn().getBody();
        if (body == null)
            body = "";

        exchange.getIn().setBody(body + String.format("Patient data for patient {%s}%n%s", patientId, fhirPatientData));
    }
}
