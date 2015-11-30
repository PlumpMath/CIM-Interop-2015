package org.endeavourhealth.async.processor;

import org.apache.camel.Exchange;
import org.endeavourhealth.common.Registry;
import org.endeavourhealth.common.core.exceptions.NoLegitimateRelationshipException;
import org.endeavourhealth.core.repository.informationSharingProtocols.InformationSharingProtocol;

public class LoadInformationSharingProtocols implements org.apache.camel.Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		String apiKey = (String)exchange.getIn().getHeader("api_key");
		InformationSharingProtocol[] protocols = Registry.Instance().getDataDistributionProtocolsForApiKey(apiKey);

		if (protocols.length == 0)
			throw new NoLegitimateRelationshipException();

		exchange.setProperty("protocols", protocols);
	}
}
