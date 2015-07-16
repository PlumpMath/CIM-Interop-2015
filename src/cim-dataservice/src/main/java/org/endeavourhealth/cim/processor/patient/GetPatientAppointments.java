package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;

import java.util.Date;
import java.util.UUID;

public class GetPatientAppointments extends CIMProcessor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);
		UUID patientId = getInHeaderUUID(exchange, HeaderKey.Patient);
		DateSearchParameter date = DateSearchParameter.Parse(getInHeaderArray(exchange, HeaderKey.Date));

		Date fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
		Date toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getAppointmentsForPatient(odsCode, patientId, fromDate, toDate);

		setInBody(exchange, body);
	}
}
