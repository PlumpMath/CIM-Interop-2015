package org.endeavourhealth.cim.repository.informationSharing.model;

import org.endeavourhealth.cim.common.models.BaseEntity;

import java.util.ArrayList;

public class DatasetCollection extends BaseEntity {
	private String name;
	private String description;
	private ArrayList<String> searchTerms = new ArrayList<>();
	private ArrayList<Integer> dataSets = new ArrayList<>();

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

	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}

	public ArrayList<Integer> getDataSets() {
		return dataSets;
	}

	@Override
	public String getSchemaVersion() {
		return "1.0";
	}
}
