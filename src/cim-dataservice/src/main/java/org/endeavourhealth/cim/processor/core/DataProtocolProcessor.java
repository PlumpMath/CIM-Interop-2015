package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.exceptions.LegitimateRelationshipException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProtocolProcessor implements org.apache.camel.Processor {
	private final Map<String, List<String>> _legitimateRelationships;

	public DataProtocolProcessor() {
		// TODO : Implement full DP logic
		_legitimateRelationships = new HashMap<>();
		_legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
		_legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = (String)exchange.getIn().getHeader("odsCode");
		String api_key = (String) exchange.getIn().getHeader("api_key");

		if (odsCode != null) {
			LoadDataProtocols(api_key, odsCode);
			ValidateLegitimateRelationships(api_key, odsCode);
		}
	}

	private void LoadDataProtocols(String api_key, String odsCode) {
		// TODO: Data protocol loading
		// Load relevant data protocols from DB

		// Set protocols in message header for processing later in pipeline
	}

	private void ValidateLegitimateRelationships(String api_key, String odsCode) throws Exception {

		List<String> validOrganisations = _legitimateRelationships.get(api_key);

		if (validOrganisations == null)
			throw new LegitimateRelationshipException("No legitimate relationships configured for this subsidiary system");

		if (!validOrganisations.contains(odsCode))
			throw new LegitimateRelationshipException("Subsidiary system has no legitimate relationship with this organisation");
	}
}
