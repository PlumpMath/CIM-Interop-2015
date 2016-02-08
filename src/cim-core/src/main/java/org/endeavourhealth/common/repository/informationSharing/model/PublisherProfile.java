package org.endeavourhealth.common.repository.informationSharing.model;

import org.endeavourhealth.core.repository.common.model.BaseEntity;

import java.util.ArrayList;
import java.util.UUID;

public class PublisherProfile extends BaseEntity {
	private UUID dataSetCollectionId;
	private ArrayList<Integer> serviceCategory = new ArrayList<>();
	private ArrayList<Integer> serviceId = new ArrayList<>();

	public String getSchemaVersion() {
		return "1.0";
	}

	public UUID getDataSetCollectionId() {
		return dataSetCollectionId;
	}
	public void setDataSetCollectionId(UUID dataSetCollectionId) {
		this.dataSetCollectionId = dataSetCollectionId;
	}

	public ArrayList<Integer> getServiceCategory() {
		return serviceCategory;
	}

	public ArrayList<Integer> getServiceId() {
		return serviceId;
	}
}
