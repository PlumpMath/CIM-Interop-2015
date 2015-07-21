package org.endeavourhealth.cim.InformationSharingFramework;

import org.endeavourhealth.cim.InformationSharingFramework.data.InformationSharingProtocolRepository;
import org.endeavourhealth.cim.InformationSharingFramework.data.ServiceByOrganisationRepository;
import org.endeavourhealth.cim.InformationSharingFramework.data.ServiceRepository;
import org.endeavourhealth.cim.InformationSharingFramework.data.SharingAgreementRepository;
import org.endeavourhealth.cim.InformationSharingFramework.model.*;
import org.endeavourhealth.cim.InformationSharingFramework.model.System;
import org.endeavourhealth.cim.common.data.RepositoryException;

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
	private ServiceRepository serviceRepository;
	private ServiceByOrganisationRepository serviceByOrganisationRepository;

	public ISFManager() {
		sharingAgreementRepository = new SharingAgreementRepository();
		protocolRepository = new InformationSharingProtocolRepository();
		serviceRepository = new ServiceRepository();
		serviceByOrganisationRepository = new ServiceByOrganisationRepository();
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
	public DataSetCollection getDataSetCollection(Integer id) { return null; }
	public DataSet getDataSet(Integer id) { return null; }
	public List<InformationSharingProtocol> getRelevantProtocols(String publisherOdsCode, String subscriberApiKey) throws RepositoryException {
		/*UUID publisherServiceId = serviceByOrganisationRepository.getById(publisherOdsCode).getServices().get(0);
		UUID subscriberServiceId = serviceByOrganisationRepository.getById(subscriberApiKey).getServices().get(0);

		SharingAgreement publisherAgreement = sharingAgreementRepository.getByServiceId(publisherServiceId);
		SharingAgreement subscriberAgreement = sharingAgreementRepository.getByServiceId(subscriberServiceId);

		return protocolRepository.getByPublisherAndSubscriberAgreementId(publisherAgreement.getId(), subscriberAgreement.getId()); */

		return protocolRepository.getByPublisherAndSubscriberAgreementId(UUID.randomUUID(), UUID.randomUUID());
	}
}
