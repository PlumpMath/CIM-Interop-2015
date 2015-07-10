package org.endeavourhealth.cim.InformationSharingFramework.model;

import java.util.ArrayList;

public class Service {
	private Integer id;
	private String name;
	private Boolean enabled;
	private ArrayList<Integer> serviceCategory = new ArrayList<>();
	private Integer organisationId;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	public Integer getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}
}
