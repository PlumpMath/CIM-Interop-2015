package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
