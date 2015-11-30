package org.endeavourhealth.cim.processor.demographics;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetAllPatientsProcessor implements Processor {
	private final boolean _activeOnly;
	public GetAllPatientsProcessor(boolean activeOnly) {
		_activeOnly = activeOnly;
	}

	public void process(Exchange exchange) throws Exception {

	}
}
