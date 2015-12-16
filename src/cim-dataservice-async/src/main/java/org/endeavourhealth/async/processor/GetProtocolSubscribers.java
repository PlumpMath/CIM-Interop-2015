package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.core.PropertyKey;

public class GetProtocolSubscribers implements org.apache.camel.Processor {
	public static final String SUBSCRIBER_LIST_PROPERTY = "SubscriberList";

	@Override
	public void process(Exchange exchange) throws Exception {
		String protocolName = exchange.getProperty(PropertyKey.InformationSharingProtocols, String.class);
		String subscribers = "log:"+protocolName+"?level=INFO";
		exchange.setProperty(SUBSCRIBER_LIST_PROPERTY, subscribers);
	}
}
