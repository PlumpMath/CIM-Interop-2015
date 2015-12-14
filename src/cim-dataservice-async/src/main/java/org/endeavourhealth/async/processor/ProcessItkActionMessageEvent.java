package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.async.camel.routes.ProcessMessageRoute;
import org.endeavourhealth.cim.transform.exceptions.SourceDocumentInvalidException;

public class ProcessItkActionMessageEvent implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String soapAction = exchange.getIn().getHeader(ProcessMessageRoute.MESSAGE_EVENT, String.class);
		if (soapAction.equals("urn:nhs-itk:services:201005:createPatient-v1-0"))
			exchange.getIn().setHeader(ProcessMessageRoute.MESSAGE_EVENT, "bulkPatient");
		else
			throw new SourceDocumentInvalidException("Message event not known");
	}
}
