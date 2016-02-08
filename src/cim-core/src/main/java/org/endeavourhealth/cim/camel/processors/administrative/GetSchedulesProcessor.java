package org.endeavourhealth.cim.camel.processors.administrative;

import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.DateUtils;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.exceptions.BaseException;
import org.endeavourhealth.cim.exceptions.InvalidParamException;
import org.endeavourhealth.cim.exceptions.MissingParamException;

import java.util.Date;

public class GetSchedulesProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = null;
		Date fromDate = null;
		Date toDate = null;
		String practitioner = null;

		try {
			odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
			DateSearchParameter date = null;

			if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.ActorPractitioner))
				practitioner = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.ActorPractitioner, false);

			if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.Date))
				date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, CIMHeaderKey.Date));

			if (practitioner == null && date == null)
				throw new MissingParamException("Either actor or date, or both must be supplied.");

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
		String responseBody = dataManager.getSchedules(odsCode, fromDate, toDate, practitioner);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
