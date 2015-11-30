package org.endeavourhealth.common.processor;

import org.apache.camel.Exchange;

public class RabbitDestinationQueueProcessor implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String apiKey = (String) exchange.getIn().getHeader("api_key");
		if (apiKey.matches("^[A-Ma-m](.*)"))
			exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "A-M");
		else if (apiKey.matches("^[N-Zn-z](.*)"))
			exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "N-Z");
		else if (apiKey.matches("^[0-9](.*)"))
			exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "0-9");
		else
			exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "DeadLetter");
	}
}
