package org.endeavourhealth.cim.processor.schedules;

import org.apache.camel.Exchange;

public class GetSchedulesProcessor implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {
		Object obj = exchange.getIn().getHeader("date");
		System.out.print(obj);
	}
}
