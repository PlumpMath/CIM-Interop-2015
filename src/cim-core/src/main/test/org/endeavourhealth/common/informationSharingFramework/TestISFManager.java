package org.endeavourhealth.common.informationSharingFramework;

import org.endeavourhealth.core.repository.informationSharing.ISFManager;
import org.endeavourhealth.core.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

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