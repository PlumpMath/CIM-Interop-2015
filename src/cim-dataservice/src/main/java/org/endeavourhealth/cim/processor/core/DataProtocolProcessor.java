package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;
import org.endeavourhealth.cim.common.exceptions.LegitimateRelationshipException;

import java.util.List;

public class DataProtocolProcessor implements org.apache.camel.Processor {
	public static final String SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION = "Subsidiary system has no legitimate relationship with this organisation";

	@Override
	public void process(Exchange exchange) throws Exception {
		String odsCode = (String)exchange.getIn().getHeader(HeaderKey.OdsCode);
		String api_key = (String) exchange.getIn().getHeader(HeaderKey.ApiKey);

//////////////////////////////////////////////////////////////
// Temporarily disable while working on ISF
//
//		if (odsCode != null)
//			LoadDataProtocols(exchange, api_key, odsCode);
//////////////////////////////////////////////////////////////
	}

	private void LoadDataProtocols(Exchange exchange, String api_key, String odsCode) throws RepositoryException, LegitimateRelationshipException {
		// Load relevant data protocols from DB
		List<InformationSharingProtocol> informationSharingProtocols = ISFManager.Instance().getRelevantProtocols(odsCode, api_key);

		ValidateLegitimateRelationships(informationSharingProtocols);

		// Set protocols in message header for processing later in pipeline
		exchange.getIn().setHeader(HeaderKey.InformationSharingProtocols, informationSharingProtocols);
	}

	private void ValidateLegitimateRelationships(List<InformationSharingProtocol> informationSharingProtocols) throws LegitimateRelationshipException {
		// If a protocol exists then there is a LR
		if (informationSharingProtocols == null || informationSharingProtocols.size() == 0)
			throw new LegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
	}
}
