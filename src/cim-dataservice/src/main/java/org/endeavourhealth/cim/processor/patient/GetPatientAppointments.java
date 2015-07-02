package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.ArrayListHelper;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class GetPatientAppointments implements Processor{
	public void process(Exchange exchange) throws Exception {
		// Get data from exchange
		UUID patientId = UUID.fromString((String) exchange.getIn().getHeader("id"));
		String odsCode = (String)exchange.getIn().getHeader("odsCode");
		DateSearchParameter date = DateSearchParameter.Parse(ArrayListHelper.FromSingleOrArray(exchange.getIn().getHeader("date")));

		// Get the relevant data adapter from the factory
		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		Date fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
		Date toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;

		String appointmentDataInNativeFormat = dataAdapter.getAppointmentsForPatient(odsCode, patientId, fromDate, toDate);
		exchange.getIn().setBody(appointmentDataInNativeFormat);

		// Get patientApi data transformer for service (native format -> FHIR)
		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);
		// Bundle appointments = transformer.toFHIRAppointments(appointmentDataInNativeFormat);
		// String body = new JsonParser().composeString(patient);
		// exchange.getIn().setBody(body);
	}
}
