package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.cim.repository.informationSharing.data.DatasetCollectionRepository;
import org.endeavourhealth.cim.repository.informationSharing.data.PublisherProfileRepository;
import org.endeavourhealth.cim.repository.informationSharing.model.DatasetCollection;
import org.endeavourhealth.cim.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.cim.repository.informationSharing.model.PublisherProfile;
import org.endeavourhealth.cim.common.HeaderKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataProtocolFilterProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        // TODO: Data protocol filtering

        // Retrieve DP from exchange
        List<InformationSharingProtocol> informationSharingProtocols = (List<InformationSharingProtocol>)exchange.getIn().getHeader(HeaderKey.InformationSharingProtocols);

		// Process protocols
		List<DatasetCollection> datasetCollections = getDataSetCollections(informationSharingProtocols);

		if (datasetCollections != null && datasetCollections.size() > 0)
			processDataSetCollections(datasetCollections);
    }

	private List<DatasetCollection> getDataSetCollections(List<InformationSharingProtocol> informationSharingProtocols) throws org.endeavourhealth.cim.common.data.RepositoryException {
		// Get profile ids from protocols
		List<UUID> publisherProfileIds = new ArrayList<>();
		for(InformationSharingProtocol protocol : informationSharingProtocols)
			publisherProfileIds.addAll(protocol.getPublisherProfiles());

		// Load dataset collection ids from profiles
		PublisherProfileRepository publisherProfileRepository = new PublisherProfileRepository();
		List<UUID> dataSetCollectionIds = new ArrayList<>();
		for (PublisherProfile publisherProfile : publisherProfileRepository.getMultipleById(publisherProfileIds))
			dataSetCollectionIds.add(publisherProfile.getDataSetCollectionId());

		// Return dataset collections
		DatasetCollectionRepository datasetCollectionRepository = new DatasetCollectionRepository();
		return datasetCollectionRepository.getMultipleById(dataSetCollectionIds);
	}

	private void processDataSetCollections(List<DatasetCollection> datasetCollections) {
	}
}
