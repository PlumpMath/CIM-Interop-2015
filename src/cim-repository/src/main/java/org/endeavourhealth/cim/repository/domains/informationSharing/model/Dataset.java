package org.endeavourhealth.cim.repository.domains.informationSharing.model;

import java.util.ArrayList;

public class Dataset {
	private Integer id;
	private String name;
	private String description;
	private ArrayList<String> searchTerms = new ArrayList<>();
	private String cohort;
	private ArrayList<IDataType> dataTypes = new ArrayList<>();

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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}

	public String getCohort() {
		return cohort;
	}
	public void setCohort(String cohort) {
		this.cohort = cohort;
	}

	public ArrayList<IDataType> getDataTypes() {
		return dataTypes;
	}
}
