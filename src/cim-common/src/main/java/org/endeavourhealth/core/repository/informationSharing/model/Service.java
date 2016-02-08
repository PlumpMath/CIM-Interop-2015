package org.endeavourhealth.core.repository.informationSharing.model;

import org.endeavourhealth.core.repository.common.model.BaseEntity;

import java.util.ArrayList;

public class Service extends BaseEntity{
	private String name;
	private Boolean enabled;
	private ArrayList<Integer> serviceCategory = new ArrayList<>();
	private String organisationId;

	public String getName() {
		return name;
	}

	public String getSchemaVersion() {
		return "1.0";
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public ArrayList<Integer> getServiceCategory() {
		return serviceCategory;
	}

	public String getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}
}
