package org.endeavourhealth.cim.InformationSharingFramework;

import org.endeavourhealth.cim.InformationSharingFramework.model.InformationSharingProtocol;
import org.endeavourhealth.cim.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class TestISFManager extends ISFManager {
	public TestISFManager() {
		// Dont call super!
	}

	@Override
	public List<InformationSharingProtocol> getRelevantProtocols(String publisherOdsCode, String subscriberApiKey) throws RepositoryException {
		List<InformationSharingProtocol> relevantProtocols = new ArrayList<>();

		// Add valid protocol for A99999 / swagger
		if ("A99999".equals(publisherOdsCode) && "swagger".equals(subscriberApiKey))
			relevantProtocols.add(new InformationSharingProtocol());

		return relevantProtocols;
	}
}