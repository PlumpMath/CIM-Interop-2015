package org.endeavourhealth.cim.InformationSharingProtocol.model;

import java.util.ArrayList;

public class DataSetCollection {
	private Integer id;
	private String name;
	private String description;
	private ArrayList<String> searchTerms = new ArrayList<>();
	private ArrayList<Integer> dataSets = new ArrayList<>();

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

	public ArrayList<Integer> getDataSets() {
		return dataSets;
	}
}
