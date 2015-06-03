package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;

import java.util.Arrays;
import java.util.Date;

public class CqlAuditParamsProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String message = (String)exchange.getIn().getBody();
        Date date = new Date();

        exchange.getIn().setBody(Arrays.asList(date, message));
    }
}
