package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.cim.transform.IRecordTransformer;
import org.endeavourhealth.async.camel.routes.TransformToFhir;

public class TransformationToFhir implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String contentType = (String)exchange.getIn().getHeader(TransformToFhir.CONTENT_TYPE);
		IRecordTransformer transformer = Registry.Instance().getTransformerForContentType(contentType);
		exchange.getIn().setBody(transformer.toFhirCareRecord(exchange.getIn().getBody(String.class)));
	}
}
