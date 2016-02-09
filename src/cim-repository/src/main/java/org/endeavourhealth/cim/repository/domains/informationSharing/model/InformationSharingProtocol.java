package org.endeavourhealth.cim.repository.domains.informationSharing.model;

import org.endeavourhealth.cim.repository.domains.informationSharing.ISFManager;
import org.endeavourhealth.cim.repository.framework.BaseEntity;

import java.util.ArrayList;
import java.util.UUID;

public class InformationSharingProtocol extends BaseEntity
{
	private String name;
	private String description;
	private Boolean enabled;
	private ConsentModel consentModel;
	private UUID derivedFromId;
	private ArrayList<UUID> publisherProfiles = new ArrayList<>();
	private ArrayList<UUID> subscriberProfiles = new ArrayList<>();
	private ArrayList<UUID> publisherSharingAgreements = new ArrayList<>();
	private ArrayList<UUID> subscriberSharingAgreements = new ArrayList<>();

	public String getName() {
		return name;
	}

	public String getSchemaVersion() {
		return "1.0";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public ConsentModel getConsentModel() {
		return consentModel;
	}
	public void setConsentModel(ConsentModel consentModel) {
		this.consentModel = consentModel;
	}

	public UUID getDerivedFromId() {
		return derivedFromId;
	}
	public void setDerivedFromId(UUID derivedFromId) {
		this.derivedFromId = derivedFromId;
	}
	public InformationSharingProtocol getDerivedFrom() { return ISFManager.Instance().getInformationSharingProtocol(derivedFromId); }

	public ArrayList<UUID> getPublisherProfiles() {
		return publisherProfiles;
	}

	public ArrayList<UUID> getSubscriberProfiles() {
		return subscriberProfiles;
	}

	public ArrayList<UUID> getPublisherSharingAgreements() {
		return publisherSharingAgreements;
	}

	public ArrayList<UUID> getSubscriberSharingAgreements() {
		return subscriberSharingAgreements;
	}
}
