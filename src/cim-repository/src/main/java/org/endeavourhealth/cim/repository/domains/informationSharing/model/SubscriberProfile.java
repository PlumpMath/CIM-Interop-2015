package org.endeavourhealth.cim.repository.domains.informationSharing.model;

import java.util.ArrayList;

public class SubscriberProfile {
	private Integer id;
	private ArrayList<Integer> userCategory = new ArrayList<>();
	private ArrayList<Integer> serviceCategory = new ArrayList<>();
	private ArrayList<Integer> serviceId = new ArrayList<>();

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public ArrayList<Integer> getUserCategory() {
		return userCategory;
	}

	public ArrayList<Integer> getServiceCategory() {
		return serviceCategory;
	}

	public ArrayList<Integer> getServiceId() {
		return serviceId;
	}
}
