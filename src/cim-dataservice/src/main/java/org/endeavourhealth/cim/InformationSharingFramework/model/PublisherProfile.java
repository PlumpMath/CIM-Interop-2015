package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.InformationSharingFramework.ISFManager;
import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;

import java.util.ArrayList;
import java.util.List;
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
