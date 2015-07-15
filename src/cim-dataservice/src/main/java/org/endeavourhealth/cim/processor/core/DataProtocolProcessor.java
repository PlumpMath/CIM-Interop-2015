package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.InformationSharingFramework.ISFManager;
import org.endeavourhealth.cim.InformationSharingFramework.model.InformationSharingProtocol;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.data.RepositoryException;
import org.endeavourhealth.cim.exceptions.LegitimateRelationshipException;

import java.util.List;

public class DataProtocolProcessor implements org.apache.camel.Processor {
	public static final String NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM = "No legitimate relationships configured for this subsidiary system";
	public static final String SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION = "Subsidiary system has no legitimate relationship with this organisation";

	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = (String)exchange.getIn().getHeader(HeaderKey.OdsCode);
		String api_key = (String) exchange.getIn().getHeader(HeaderKey.ApiKey);

		if (odsCode != null) {
			LoadDataProtocols(exchange, api_key, odsCode);
			ValidateLegitimateRelationships(api_key, odsCode);
		}
	}

	private void LoadDataProtocols(Exchange exchange, String api_key, String odsCode) throws RepositoryException {
		// Load relevant data protocols from DB
		List<InformationSharingProtocol> informationSharingProtocols = ISFManager.Instance().getRelevantProtocols(odsCode, api_key);

		// Set protocols in message header for processing later in pipeline
		exchange.getIn().setHeader(HeaderKey.InformationSharingProtocols, informationSharingProtocols);
	}

	private void ValidateLegitimateRelationships(String api_key, String odsCode) throws Exception {

		List<String> validOrganisations = ISFManager.Instance().getLegitimateRelationships().get(api_key);

		if (validOrganisations == null)
			throw new LegitimateRelationshipException(NO_LEGITIMATE_RELATIONSHIPS_CONFIGURED_FOR_THIS_SUBSIDIARY_SYSTEM);

		if (!validOrganisations.contains(odsCode))
			throw new LegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
	}
}
