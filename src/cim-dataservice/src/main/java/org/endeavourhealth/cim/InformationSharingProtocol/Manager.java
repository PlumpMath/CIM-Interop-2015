package org.endeavourhealth.cim.InformationSharingProtocol;

import org.endeavourhealth.cim.InformationSharingProtocol.model.*;
import org.endeavourhealth.cim.InformationSharingProtocol.model.System;

import java.util.ArrayList;
import java.util.List;

public class Manager {
	private static Manager _instance = new Manager();
	public static Manager Instance() { return _instance; }

	public InformationSharingProtocol getInformationSharingProtocol(Integer id) { return null; }
	public SharingAgreement getSharingAgreement(Integer id) { return null; }
	public TechnicalInterface getTechnicalInterface(Integer id) { return null; }
	public System getSystem(Integer id) { return null; }
	public PublisherProfile getPublisherProfile(Integer id) { return null; }
	public SubscriberProfile getSubscriberProfile(Integer id) { return null; }
	public Service getService(Integer id) { return null; }
	public DataSetCollection getDataSetCollection(Integer id) { return null; }
	public DataSet getDataSet(Integer id) { return null; }
	public List<InformationSharingProtocol> getReleventProtocols(Integer publisherId, Integer subscriberId) {
		return new ArrayList<>();
	}
}
