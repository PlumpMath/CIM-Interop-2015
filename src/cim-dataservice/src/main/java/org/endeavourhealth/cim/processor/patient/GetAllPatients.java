package org.endeavourhealth.cim.processor.patient;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetAllPatients implements Processor {
	private final boolean _activeOnly;
	public GetAllPatients(boolean activeOnly) {
		_activeOnly = activeOnly;
	}

	public void process(Exchange exchange) throws Exception {

	}
}
