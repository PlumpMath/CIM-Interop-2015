package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.dataManager.DataManagerFactory;
import org.endeavourhealth.cim.common.ArrayListHelper;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.dataManager.IDataManager;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public static final String EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED = "Either an actor, a date range or both must be supplied.";

	public void process(Exchange exchange) throws Exception {

		String odsCode = (String)exchange.getIn().getHeader("odsCode");
		DateSearchParameter date = DateSearchParameter.Parse(ArrayListHelper.FromSingleOrArray(exchange.getIn().getHeader("date")));
		String practitioner = (String)exchange.getIn().getHeader("actor:Practitioner");

		if ((practitioner == null && date == null) || (practitioner != null && date != null) || (date != null) && (date.getIntervalStart().equals(date.getIntervalEnd())))
			throw new IllegalArgumentException(EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		IDataManager dataManager = DataManagerFactory.getDataManagerForService(odsCode);
		String body;
		if (date != null) {
			body = dataManager.getSchedules(odsCode, date.getIntervalStart(), date.getIntervalEnd());
		} else
			body = dataManager.getSchedules(odsCode, practitioner);

		exchange.getIn().setBody(body);
	}
}
