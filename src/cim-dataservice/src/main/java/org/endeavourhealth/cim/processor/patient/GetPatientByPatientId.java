package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.common.IDataAdapter;
import org.endeavourhealth.cim.common.ITransformer;
import org.endeavourhealth.cim.transformer.TransformerFactory;

import java.util.UUID;

public class GetPatientByPatientId implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {
        String patientId = (String)exchange.getIn().getHeader("patientId");
        int serviceId = Integer.parseInt((String) exchange.getIn().getHeader("serviceId"));

        // Get patientApi data (native format) using adapter
        IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(serviceId);
        String patientData = dataAdapter.getPatientByPatientId(UUID.fromString(patientId));

        // Get patientApi data transformer for service (native format -> FHIR)
        ITransformer transformer = TransformerFactory.getTransformerForService(serviceId);

        if (transformer == null)    // No transformation required
            return;

        // Transform from native format to FHIR
        String fhirPatientData = transformer.toFHIR(patientData);

        String body = (String) exchange.getIn().getBody();
        if (body == null)
            body = "";

        exchange.getIn().setBody(body + String.format("Patient data for patient {%s}%n%s", patientId, fhirPatientData));
    }
}
