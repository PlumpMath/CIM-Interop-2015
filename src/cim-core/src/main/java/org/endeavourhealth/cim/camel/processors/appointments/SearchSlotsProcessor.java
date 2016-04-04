package org.endeavourhealth.cim.camel.processors.appointments;

import org.endeavourhealth.cim.camel.exceptions.BaseException;
import org.endeavourhealth.cim.camel.exceptions.InvalidParamException;
import org.endeavourhealth.cim.camel.exceptions.MissingParamException;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.DateSearchParameter;
import org.endeavourhealth.cim.camel.helpers.DateUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.dataManager.emis.DataManager;

import java.util.Date;
import java.util.UUID;

public class SearchSlotsProcessor implements Processor
{
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception
	{
		String odsCode = null;
		Date fromDate = null;
		Date toDate = null;
		UUID practitioner = null;

		try
		{
			odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
			DateSearchParameter date = null;

			if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.ActorPractitioner))
				practitioner = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.ActorPractitioner, false);

			if (ExchangeHelper.hasInHeader(exchange, CIMHeaderKey.Date))
				date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, CIMHeaderKey.Date));

			if (practitioner == null && date == null)
				throw new MissingParamException("Either actor or date, or both must be supplied.");

			fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
			toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;
		}
		catch (BaseException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new InvalidParamException("Error parsing parameters.", e);
		}

		DataManager dataManager = new DataManager();
		String responseBody = dataManager.searchSlots(odsCode, fromDate, toDate, practitioner);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
