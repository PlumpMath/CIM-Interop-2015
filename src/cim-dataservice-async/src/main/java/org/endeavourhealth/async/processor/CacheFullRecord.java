package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;

public class CacheFullRecord implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		// Store FHIR record in DB as requried
	}
}
