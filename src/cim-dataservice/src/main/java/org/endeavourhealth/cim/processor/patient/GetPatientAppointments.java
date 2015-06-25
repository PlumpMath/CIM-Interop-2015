package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;

import java.util.UUID;

public class GetPatientAppointments implements Processor{
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		UUID patientId = UUID.fromString((String)exchange.getIn().getHeader("id"));
		String odsCode = (String) exchange.getIn().getHeader("odsCode");

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		// Get patient data by NHS Number
		String appointmentDataInNativeFormat = dataAdapter.getAppointmentsForPatient(odsCode, patientId);
		exchange.getIn().setBody(appointmentDataInNativeFormat);

		// Get patientApi data transformer for service (native format -> FHIR)
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		// Bundle appointments = transformer.toFHIRAppointments(appointmentDataInNativeFormat);
		// String body = new JsonParser().composeString(patient);
		// exchange.getIn().setBody(body);
	}
}
