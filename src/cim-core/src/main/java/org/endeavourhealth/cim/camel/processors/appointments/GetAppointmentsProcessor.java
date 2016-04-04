package org.endeavourhealth.cim.camel.processors.appointments;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.camel.helpers.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.exceptions.BaseException;
import org.endeavourhealth.cim.camel.exceptions.InvalidParamException;
import org.endeavourhealth.cim.camel.helpers.DateUtils;

import java.util.Date;
import java.util.UUID;

public class GetAppointmentsProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode;
		UUID patientId;
		Date fromDate;
		Date toDate;

		try
		{
			odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
			patientId = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Patient, true);

			DateSearchParameter date = null;

			if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.Date))
				date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, CIMHeaderKey.Date));

			fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
			toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;
		}
		catch (BaseException e) {
			throw e;
		}
		catch (Exception e) {
			throw new InvalidParamException("Error parsing parameters.", e);
		}

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getAppointmentsForPatient(odsCode, patientId, fromDate, toDate);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
