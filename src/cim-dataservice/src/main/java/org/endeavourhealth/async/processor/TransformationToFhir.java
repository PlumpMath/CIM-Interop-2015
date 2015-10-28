package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.transform.IRecordTransformer;

public class TransformationToFhir implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String apiKey = (String)exchange.getIn().getHeader("api_key");
		IRecordTransformer transformer = Registry.Instance().getTransformerForApiKey(apiKey);
		exchange.getIn().setBody(transformer.toFhirCareRecord(exchange.getIn().getBody(String.class)));
	}
}
