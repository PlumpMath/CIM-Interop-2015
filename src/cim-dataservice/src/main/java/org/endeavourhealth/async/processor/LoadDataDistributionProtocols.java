package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.core.dataDistributionProtocols.DataDistributionProtocol;

public class LoadDataDistributionProtocols implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String apiKey = (String)exchange.getIn().getHeader("api_key");
		DataDistributionProtocol[] protocols = Registry.Instance().getDataDistributionProtocolsForApiKey(apiKey);
	}
}
