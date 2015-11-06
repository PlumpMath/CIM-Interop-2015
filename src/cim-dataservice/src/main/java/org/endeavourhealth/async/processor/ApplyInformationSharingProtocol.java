package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.core.repository.informationSharingProtocols.InformationSharingProtocol;

public class ApplyInformationSharingProtocol implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		InformationSharingProtocol[] protocols = (InformationSharingProtocol[])exchange.getProperty("protocols");
	}
}
