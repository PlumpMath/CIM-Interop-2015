package org.endeavourhealth.common.repository.informationSharing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProtocolByPublisherSubscriberAgreement {
	private UUID publisherAgreementId;
	private UUID subscriberAgreementId;
	private ArrayList<UUID> protocols = new ArrayList<>();

	public String getSchemaVersion() { return "1.0"; }

	public UUID getPublisherAgreementId() {
		return publisherAgreementId;
	}

	public void setPublisherAgreementId(UUID publisherAgreementId) {
		this.publisherAgreementId = publisherAgreementId;
	}

	public UUID getSubscriberAgreementId() {
		return subscriberAgreementId;
	}

	public void setSubscriberAgreementId(UUID subscriberAgreementId) {
		this.subscriberAgreementId = subscriberAgreementId;
	}

	public List<UUID> getProtocols() {
		return protocols;
	}
}
