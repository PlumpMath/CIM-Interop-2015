package org.endeavourhealth.cim.camel.processors.common;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.camel.helpers.CIMHeaderKey;
import org.endeavourhealth.cim.camel.helpers.ExchangeHelper;
import org.endeavourhealth.cim.camel.helpers.PropertyKey;
import org.endeavourhealth.cim.exceptions.NoLegitimateRelationshipException;
import org.endeavourhealth.core.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadDataProtocols implements org.apache.camel.Processor {
	public static final String SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION = "Subsidiary system has no legitimate relationship with this organisation";

	@Override
	public void process(Exchange exchange) throws Exception {
		// temporary check
		List validOdsCodes = Arrays.asList("A99999", "B99999", "D99999", "Y99999", "Z99999");
		String odsCode = ExchangeHelper.getInHeaderString(exchange, CIMHeaderKey.DestinationOdsCode);
 		if (TextUtils.isNullOrTrimmedEmpty(odsCode) == false) {
			if (validOdsCodes.contains(odsCode)) {
				// LR check pass
			} else
				throw new NoLegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
		}
		// end temporary check

//		String sourceOdsCode = exchange.getIn().getHeader(PropertyKey.SourceOdsCode, String.class);
//
//		if (sourceOdsCode != null)
//			LoadDataProtocols(exchange, sourceOdsCode);
	}

	private void LoadDataProtocols(Exchange exchange, String sourceOdsCode) throws RepositoryException, NoLegitimateRelationshipException {
		// Load relevant data protocols from DB
		List<InformationSharingProtocol> informationSharingProtocols;

		//informationSharingProtocols = ISFManager.Instance().getRelevantProtocols(sourceOdsCode);
		informationSharingProtocols = new ArrayList<>();
		informationSharingProtocols.add(new InformationSharingProtocol() {{ setName("VitruCare"); }});
		informationSharingProtocols.add(new InformationSharingProtocol() {{ setName("PharmacyApp"); }});
		informationSharingProtocols.add(new InformationSharingProtocol() {{ setName("AnalyticsDB"); }});
		ValidateLegitimateRelationships(informationSharingProtocols);

		// Set protocols in message header for processing later in pipeline
		exchange.setProperty(PropertyKey.InformationSharingProtocols, informationSharingProtocols);
	}

	private void ValidateLegitimateRelationships(List<InformationSharingProtocol> informationSharingProtocols) throws NoLegitimateRelationshipException {
		// If a protocol exists then there is a LR
		if (informationSharingProtocols == null || informationSharingProtocols.size() == 0)
			throw new NoLegitimateRelationshipException(SUBSIDIARY_SYSTEM_HAS_NO_LEGITIMATE_RELATIONSHIP_WITH_THIS_ORGANISATION);
	}
}
