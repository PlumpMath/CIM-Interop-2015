package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ResponseProcessor implements Processor{
    public void process(Exchange exchange) throws Exception {
        // Strips extraneous header info/debug data
        exchange.getOut().setBody(exchange.getIn().getBody());
    }
}
