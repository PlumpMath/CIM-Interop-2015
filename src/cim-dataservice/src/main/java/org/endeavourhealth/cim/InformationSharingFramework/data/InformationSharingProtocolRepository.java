package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.InformationSharingProtocol;
import org.endeavourhealth.cim.InformationSharingFramework.model.SharingAgreement;
import org.endeavourhealth.cim.common.data.GenericRepository;

import java.util.List;
import java.util.UUID;

public class InformationSharingProtocolRepository extends GenericRepository<InformationSharingProtocol> {

	public InformationSharingProtocolRepository() {
		super(InformationSharingProtocol.class);
	}

	public List<InformationSharingProtocol> getByPublisherAndSubscriberAgreementId(UUID publisherAgreementId, UUID subscriberAgreementId) {
		return null;
	}
}
