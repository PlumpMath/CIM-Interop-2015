package org.endeavourhealth.cim.InformationSharingProtocol.model;

import org.endeavourhealth.cim.InformationSharingProtocol.Manager;

public class SharingAgreement {
	private Integer id;
	private Integer serviceId;
	private Integer technicalInterfaceId;
	private Boolean active;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getTechnicalInterfaceId() {
		return technicalInterfaceId;
	}
	public void setTechnicalInterfaceId(Integer technicalInterfaceId) {
		this.technicalInterfaceId = technicalInterfaceId;
	}
	public TechnicalInterface getTechnicalInterface() {
		return Manager.Instance().getTechnicalInterface(technicalInterfaceId);
	}

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}
