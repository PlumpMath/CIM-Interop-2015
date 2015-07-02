package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.exceptions.LegitimateRelationshipException;

import java.util.List;

public class DataProtocolProcessor implements org.apache.camel.Processor {
	public static final String NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM = "No legitimate relationships configured for this subsidiary system";
	public static final String SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION = "Subsidiary system has no legitimate relationship with this organisation";

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

		List<String> validOrganisations = Registry.Instance().getLegitimateRelationships().get(api_key);

		if (validOrganisations == null)
			throw new LegitimateRelationshipException(NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM);

		if (!validOrganisations.contains(odsCode))
			throw new LegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
	}
}
