package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.ArrayListHelper;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public static final String EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED = "Either an actor, a date range or both must be supplied.";

	public void process(Exchange exchange) throws Exception {

		String odsCode = (String)exchange.getIn().getHeader("odsCode");
		DateSearchParameter date = DateSearchParameter.Parse(ArrayListHelper.FromSingleOrArray(exchange.getIn().getHeader("date")));
		String practitioner = (String)exchange.getIn().getHeader("actor:Practitioner");

		if ((practitioner == null && date == null) || (practitioner != null && date != null) || (date != null) && (date.getIntervalStart().equals(date.getIntervalEnd())))
			throw new IllegalArgumentException(EITHER_AN_ACTOR_A_DATE_RANGE_OR_BOTH_MUST_BE_SUPPLIED);

		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		String schedulesInNativeFormat;
		if (date != null) {
			schedulesInNativeFormat = dataAdapter.getSchedules(odsCode, date.getIntervalStart(), date.getIntervalEnd());
		} else
			schedulesInNativeFormat = dataAdapter.getSchedules(odsCode, practitioner);

		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);

		Bundle bundle = transformer.toFHIRScheduleBundle(schedulesInNativeFormat);
		String body = new JsonParser().composeString(bundle);

		exchange.getIn().setBody(body);
	}
}
