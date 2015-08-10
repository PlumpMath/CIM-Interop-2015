package org.endeavourhealth.cim.repository.informationSharing;

import org.endeavourhealth.cim.repository.informationSharing.data.*;
import org.endeavourhealth.cim.repository.informationSharing.model.*;
import org.endeavourhealth.cim.repository.informationSharing.model.System;
import org.endeavourhealth.cim.common.repository.common.data.RepositoryException;

import java.util.*;

public class ISFManager {
	private static ISFManager _instance;
	public static ISFManager Instance() {
		if (_instance == null)
			_instance = new ISFManager();

		return _instance;
	}
	public static void setInstance(ISFManager isfManager) { _instance = isfManager; }

	private SharingAgreementRepository sharingAgreementRepository;
	private InformationSharingProtocolRepository protocolRepository;
	private ServiceByOrganisationRepository serviceByOrganisationRepository;
	private AgreementByServiceRepository agreementByServiceRepository;
	private ProtocolByPublisherSubscriberAgreementRepository protocolByPublisherSubscriberAgreementRepository;

	public ISFManager() {
		sharingAgreementRepository = new SharingAgreementRepository();
		protocolRepository = new InformationSharingProtocolRepository();
		serviceByOrganisationRepository = new ServiceByOrganisationRepository();
		agreementByServiceRepository = new AgreementByServiceRepository();
		protocolByPublisherSubscriberAgreementRepository = new ProtocolByPublisherSubscriberAgreementRepository();
	}

	public InformationSharingProtocol getInformationSharingProtocol(UUID id) { return null; }
	public SharingAgreement getSharingAgreement(UUID id) throws RepositoryException {
		return sharingAgreementRepository.getById(id);
	}
	public TechnicalInterface getTechnicalInterface(UUID id) { return null; }
	public System getSystem(UUID id) { return null; }
	public PublisherProfile getPublisherProfile(Integer id) { return null; }
	public SubscriberProfile getSubscriberProfile(Integer id) { return null; }
	public Service getService(Integer id) { return null; }
	public Dataset getDataSet(Integer id) { return null; }
	public List<InformationSharingProtocol> getRelevantProtocols(String publisherOdsCode, String subscriberApiKey) throws RepositoryException {
		UUID publisherServiceId = serviceByOrganisationRepository.getById(publisherOdsCode).getServices().get(0);
		UUID subscriberServiceId = serviceByOrganisationRepository.getById(subscriberApiKey).getServices().get(0);

		UUID publisherAgreementId = agreementByServiceRepository.getById(publisherServiceId).getAgreements().get(0);
		UUID subscriberAgreementId = agreementByServiceRepository.getById(subscriberServiceId).getAgreements().get(0);

		ProtocolByPublisherSubscriberAgreement protocolByPublisherSubscriberAgreement = protocolByPublisherSubscriberAgreementRepository.getById(publisherAgreementId, subscriberAgreementId);

		return protocolRepository.getMultipleById(protocolByPublisherSubscriberAgreement.getProtocols());
	}
}
