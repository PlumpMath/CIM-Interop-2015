package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.common.ExchangeHelper;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.exceptions.CIMException;
import org.endeavourhealth.cim.common.exceptions.CIMInvalidParamException;
import org.endeavourhealth.cim.common.exceptions.CIMMissingParamException;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;

import java.util.Date;

public class GetSchedulesProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		String odsCode = null;
		String practitioner = null;
		Date fromDate = null;
		Date toDate = null;

		try {
			odsCode = ExchangeHelper.getInHeaderString(exchange, HeaderKey.OdsCode);
			DateSearchParameter date = DateSearchParameter.Parse(ExchangeHelper.getInHeaderArray(exchange, HeaderKey.Date));
			practitioner = ExchangeHelper.getInHeaderString(exchange, HeaderKey.ActorPractitioner);

			if (practitioner == null && date == null)
				throw new CIMMissingParamException("Either actor or date, or both must be supplied.");

			fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
			toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;
		}
		catch (CIMException e) {
			throw e;
		}
		catch (Exception e) {
			throw new CIMInvalidParamException("Error parsing parameters.", e);
		}

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String responseBody = dataManager.getSchedules(odsCode, fromDate, toDate, practitioner);

		ExchangeHelper.setInBodyString(exchange, responseBody);
	}
}
