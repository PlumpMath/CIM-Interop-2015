package org.endeavourhealth.cim.processor.administrative;

import org.endeavourhealth.cim.common.CIMHeaderKey;
import org.endeavourhealth.cim.common.searchParameters.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.DateUtils;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.exceptions.BaseException;
import org.endeavourhealth.common.core.exceptions.InvalidParamException;
import org.endeavourhealth.common.core.exceptions.MissingParamException;

import java.util.Date;

public class GetSchedulesProcessor implements Processor {

	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {

		String odsCode = null;
		Date fromDate = null;
		Date toDate = null;
		String practitioner = null;

		try {
			odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.DestinationOdsCode, true);
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
