package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.Date;
import java.util.UUID;

public class GetAppointmentsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
		UUID patientId = ExchangeHelper.getInHeaderUUID(exchange, HeaderKey.Patient);
		DateSearchParameter date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, HeaderKey.Date));

		Date fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
		Date toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getAppointmentsForPatient(odsCode, patientId, fromDate, toDate);

		ExchangeHelper.setInBodyString(exchange, body);
	}
}
