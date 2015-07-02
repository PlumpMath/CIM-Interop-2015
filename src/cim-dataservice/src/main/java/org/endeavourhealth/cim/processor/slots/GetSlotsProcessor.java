package org.endeavourhealth.cim.processor.slots;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;
import org.endeavourhealth.cim.common.ArrayListHelper;
import org.endeavourhealth.cim.common.DateSearchParameter;
import org.endeavourhealth.cim.common.TextUtils;
import org.endeavourhealth.cim.transform.Transformer;
import org.endeavourhealth.cim.transform.TransformerFactory;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetSlotsProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {

		String odsCode = (String) exchange.getIn().getHeader("odsCode");
		String scheduleId = (String)exchange.getIn().getHeader("schedule");
		DateSearchParameter dateSearchParameter = DateSearchParameter.Parse(ArrayListHelper.FromSingleOrArray(exchange.getIn().getHeader("start")));

		IDataAdapter dataAdapter = AdapterFactory.getDataAdapterForService(odsCode);

		String slotsInNativeFormat = dataAdapter.getSlots(odsCode, scheduleId);

		Transformer transformer = TransformerFactory.getTransformerForAdapter(dataAdapter);

		Bundle bundle = transformer.toFHIRSlotBundle(slotsInNativeFormat, scheduleId);
		String body = new JsonParser().composeString(bundle);

		exchange.getIn().setBody(body);
	}
}
