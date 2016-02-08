package org.endeavourhealth.common.repository.informationSharing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceByOrganisation {
	private String id;
	private ArrayList<UUID> services = new ArrayList<>();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSchemaVersion() { return "1.0"; }

	public List<UUID> getServices() {
		return services;
	}
}
