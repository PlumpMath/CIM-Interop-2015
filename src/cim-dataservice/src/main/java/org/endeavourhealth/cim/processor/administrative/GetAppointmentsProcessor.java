package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.exceptions.CIMException;
import org.endeavourhealth.cim.common.exceptions.CIMInvalidParamException;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.Date;
import java.util.UUID;

public class GetAppointmentsProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode;
		UUID patientId;
		Date fromDate;
		Date toDate;

		try {
			odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
			patientId = ExchangeHelper.getInHeaderUUID(exchange, HeaderKey.Patient);

			DateSearchParameter date = null;

			if (ExchangeHelper.hasInHeader(exchange, HeaderKey.Date))
				date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, HeaderKey.Date));

			fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
			toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;
		}
		catch (Exception e) {
			throw new CIMInvalidParamException("Error parsing parameters.", e);
		}

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getAppointmentsForPatient(odsCode, patientId, fromDate, toDate);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
