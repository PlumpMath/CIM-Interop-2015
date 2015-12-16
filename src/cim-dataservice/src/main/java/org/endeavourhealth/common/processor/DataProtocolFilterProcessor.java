package org.endeavourhealth.common.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.core.PropertyKey;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.common.repository.informationSharing.data.DatasetCollectionRepository;
import org.endeavourhealth.common.repository.informationSharing.data.PublisherProfileRepository;
import org.endeavourhealth.common.repository.informationSharing.model.DatasetCollection;
import org.endeavourhealth.common.repository.informationSharing.model.InformationSharingProtocol;
import org.endeavourhealth.common.repository.informationSharing.model.PublisherProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataProtocolFilterProcessor implements Processor {
	private Logger log = LoggerFactory.getLogger(DataProtocolFilterProcessor.class);

    public void process(Exchange exchange) throws Exception {
		InformationSharingProtocol informationSharingProtocol = (InformationSharingProtocol)exchange.getProperty(PropertyKey.InformationSharingProtocols);
		if (informationSharingProtocol != null)
			log.info("Processing protocol "+informationSharingProtocol.getName());

//////////////////////////////////////////////////////////////
// Temporarily disable while working on ISF
//
//		// TODO: Data protocol filtering
//
//        // Retrieve DP from exchange
//        List<InformationSharingProtocol> informationSharingProtocols = (List<InformationSharingProtocol>)exchange.getIn().getHeader(HeaderKey.InformationSharingProtocols);
//
//		// Process protocols
//		List<DatasetCollection> datasetCollections = getDataSetCollections(informationSharingProtocols);
//
//		if (datasetCollections != null && datasetCollections.size() > 0)
//			processDataSetCollections(datasetCollections);
//////////////////////////////////////////////////////////////
    }

	private List<DatasetCollection> getDataSetCollections(List<InformationSharingProtocol> informationSharingProtocols) throws RepositoryException {
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
