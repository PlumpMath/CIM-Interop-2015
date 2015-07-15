package org.endeavourhealth.cim.InformationSharingFramework;

import org.endeavourhealth.cim.InformationSharingFramework.data.InformationSharingProtocolRepository;
import org.endeavourhealth.cim.InformationSharingFramework.data.ServiceRepository;
import org.endeavourhealth.cim.InformationSharingFramework.data.SharingAgreementRepository;
import org.endeavourhealth.cim.InformationSharingFramework.model.*;
import org.endeavourhealth.cim.InformationSharingFramework.model.System;
import org.endeavourhealth.cim.common.data.RepositoryException;

import java.util.*;

public class ISFManager {
	private static ISFManager _instance = new ISFManager();
	public static ISFManager Instance() { return _instance; }

	SharingAgreementRepository sharingAgreementRepository = new SharingAgreementRepository();
	InformationSharingProtocolRepository protocolRepository = new InformationSharingProtocolRepository();
	ServiceRepository serviceRepository = new ServiceRepository();

	public InformationSharingProtocol getInformationSharingProtocol(Integer id) { return null; }
	public SharingAgreement getSharingAgreement(UUID id) throws RepositoryException {
		return sharingAgreementRepository.getById(id);
	}
	public TechnicalInterface getTechnicalInterface(Integer id) { return null; }
	public System getSystem(Integer id) { return null; }
	public PublisherProfile getPublisherProfile(Integer id) { return null; }
	public SubscriberProfile getSubscriberProfile(Integer id) { return null; }
	public Service getService(Integer id) { return null; }
	public DataSetCollection getDataSetCollection(Integer id) { return null; }
	public DataSet getDataSet(Integer id) { return null; }
	public List<InformationSharingProtocol> getRelevantProtocols(String publisherOdsCode, String subscriberApiKey) throws RepositoryException {
		Service publisherService = serviceRepository.getByOdsCode(publisherOdsCode);
		Service subscriberService = serviceRepository.getByPublicApiKey(subscriberApiKey);

		SharingAgreement publisherAgreement = sharingAgreementRepository.getByServiceId(publisherService.getId());
		SharingAgreement subscriberAgreement = sharingAgreementRepository.getByServiceId(subscriberService.getId());

		return protocolRepository.getByPublisherAndSubscriberAgreementId(publisherAgreement.getId(), subscriberAgreement.getId());
	}

	public Map<String, List<String>> getLegitimateRelationships() {
			// TODO : Implement full DP logic
			Map<String, List<String>> _legitimateRelationships = new HashMap<>();
			_legitimateRelationships.put("swagger", Arrays.asList("A99999", "B99999"));
			_legitimateRelationships.put("subsidiary", Arrays.asList("Y99999", "Z99999"));
			return _legitimateRelationships;
	}
}
