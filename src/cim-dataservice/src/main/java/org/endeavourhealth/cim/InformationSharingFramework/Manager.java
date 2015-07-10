package org.endeavourhealth.cim.InformationSharingFramework;

import org.endeavourhealth.cim.InformationSharingFramework.data.SharingAgreementRepository;
import org.endeavourhealth.cim.InformationSharingFramework.model.*;
import org.endeavourhealth.cim.InformationSharingFramework.model.System;
import org.endeavourhealth.cim.common.data.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Manager {
	private static Manager _instance = new Manager();
	public static Manager Instance() { return _instance; }

	public InformationSharingProtocol getInformationSharingProtocol(Integer id) { return null; }
	public SharingAgreement getSharingAgreement(UUID id) throws RepositoryException {
		SharingAgreementRepository repository = new SharingAgreementRepository();
		return repository.getById(id);
	}
	public TechnicalInterface getTechnicalInterface(Integer id) { return null; }
	public System getSystem(Integer id) { return null; }
	public PublisherProfile getPublisherProfile(Integer id) { return null; }
	public SubscriberProfile getSubscriberProfile(Integer id) { return null; }
	public Service getService(Integer id) { return null; }
	public DataSetCollection getDataSetCollection(Integer id) { return null; }
	public DataSet getDataSet(Integer id) { return null; }
	public List<InformationSharingProtocol> getReleventProtocols(Integer publisherId, Integer subscriberId) {
		ArrayList<InformationSharingProtocol> relevantProtocols = new ArrayList<>();

		return relevantProtocols;
	}
}
