package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.InformationSharingFramework.Manager;

import java.util.ArrayList;

public class InformationSharingProtocol {
	private Integer id;
	private String name;
	private String description;
	private Boolean enabled;
	private ConsentModel consentModel;
	private Integer derivedFromId;
	private ArrayList<Integer> publisherProfiles = new ArrayList<>();
	private ArrayList<Integer> subscriberProfiles = new ArrayList<>();
	private ArrayList<Integer> publisherSharingAgreement = new ArrayList<>();
	private ArrayList<Integer> subscriberSharingAgreement = new ArrayList<>();

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getName() {
		return name;
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

	public Integer getDerivedFromId() {
		return derivedFromId;
	}
	public void setDerivedFromId(Integer derivedFromId) {
		this.derivedFromId = derivedFromId;
	}
	public InformationSharingProtocol getDerivedFrom() { return Manager.Instance().getInformationSharingProtocol(derivedFromId); }

	public ArrayList<Integer> getPublisherProfiles() {
		return publisherProfiles;
	}

	public ArrayList<Integer> getSubscriberProfiles() {
		return subscriberProfiles;
	}

	public ArrayList<Integer> getPublisherSharingAgreement() {
		return publisherSharingAgreement;
	}

	public ArrayList<Integer> getSubscriberSharingAgreement() {
		return subscriberSharingAgreement;
	}
}
