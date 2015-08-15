package org.endeavourhealth.cim.processor.administrative;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.common.DateUtils;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;
import org.endeavourhealth.cim.processor.CIMProcessor;

import java.util.Date;

public class GetSchedulesProcessor extends CIMProcessor {
	public static final String EITHER_ACTOR_OR_DATE_OR_BOTH_MUST_BE_SUPPLIED = "Either actor or date, or both must be supplied.";

	public void process(Exchange exchange) throws Exception {

		String odsCode = getInHeaderString(exchange, HeaderKey.OdsCode);
		DateSearchParameter date = DateSearchParameter.Parse(getInHeaderArray(exchange, HeaderKey.Date));
		String practitioner = getInHeaderString(exchange, HeaderKey.ActorPractitioner);

		if (practitioner == null && date == null)
			throw new IllegalArgumentException(EITHER_ACTOR_OR_DATE_OR_BOTH_MUST_BE_SUPPLIED);

		Date fromDate = (date != null) ? date.getIntervalStart() : DateUtils.DOTNET_MINIMUM_DATE;
		Date toDate = (date != null) ? date.getIntervalEnd() : DateUtils.DOTNET_MAXIMUM_DATE;

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body = dataManager.getSchedules(odsCode, fromDate, toDate, practitioner);

		setInBodyString(exchange, body);
	}
}
