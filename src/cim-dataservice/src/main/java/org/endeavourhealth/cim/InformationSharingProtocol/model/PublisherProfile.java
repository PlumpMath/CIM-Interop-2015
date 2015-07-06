package org.endeavourhealth.cim.InformationSharingProtocol.model;

import org.endeavourhealth.cim.InformationSharingProtocol.Manager;

import java.util.ArrayList;

public class PublisherProfile {
	private Integer id;
	private Integer dataSetCollectionId;
	private ArrayList<Integer> serviceCategory = new ArrayList<>();
	private ArrayList<Integer> serviceId = new ArrayList<>();

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataSetCollectionId() {
		return dataSetCollectionId;
	}
	public void setDataSetCollectionId(Integer dataSetCollectionId) {
		this.dataSetCollectionId = dataSetCollectionId;
	}
	public DataSetCollection getDataSetCollection() { return Manager.Instance().getDataSetCollection(dataSetCollectionId); }

	public ArrayList<Integer> getServiceCategory() {
		return serviceCategory;
	}

	public ArrayList<Integer> getServiceId() {
		return serviceId;
	}
}
