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
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SearchSlotsProcessor implements Processor
{
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception
	{
		String odsCode = null;
		DateSearchParameter date = null;
		UUID location = null;

		try
		{
			odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode, true);
			date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, CIMHeaderKey.Date, true));
			location = ExchangeHelper.getInHeaderUUID(exchange, CIMHeaderKey.Location, false);

			if (TimeUnit.DAYS.convert(date.getIntervalEnd().getTime() - date.getIntervalStart().getTime(), TimeUnit.MILLISECONDS) > 14)
				throw new InvalidParamException("Time range must be less than 14 days");
		}
		catch (BaseException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new InvalidParamException("Error parsing parameters.", e);
		}

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.searchSlots(odsCode, date.getIntervalStart(), date.getIntervalEnd(), location);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
