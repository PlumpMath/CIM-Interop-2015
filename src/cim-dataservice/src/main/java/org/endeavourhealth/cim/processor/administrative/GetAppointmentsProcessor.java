package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.searchParameters.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.BaseException;
import org.endeavourhealth.common.core.exceptions.InvalidParamException;
import org.endeavourhealth.common.core.DateUtils;

import java.util.Date;

public class GetAppointmentsProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode;
		String patientId;
		Date fromDate;
		Date toDate;

		try {
			odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
			patientId = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.Patient, true);

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
