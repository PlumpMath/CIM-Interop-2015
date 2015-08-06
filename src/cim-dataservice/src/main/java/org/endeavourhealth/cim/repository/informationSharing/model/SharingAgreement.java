package org.endeavourhealth.cim.repository.informationSharing.model;

import org.endeavourhealth.cim.repository.informationSharing.ISFManager;
import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;

import java.util.List;
import java.util.UUID;

public class SharingAgreement extends BaseEntity {
	private String name;
	private List<EntityIdentifier> identifiers;
	private Integer serviceId;
	private UUID technicalInterfaceId;
	private Boolean active;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSchemaVersion() { return "1.0"; }

	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public UUID getTechnicalInterfaceId() {
		return technicalInterfaceId;
	}
	public void setTechnicalInterfaceId(UUID technicalInterfaceId) {
		this.technicalInterfaceId = technicalInterfaceId;
	}
	public TechnicalInterface getTechnicalInterface() {
		return ISFManager.Instance().getTechnicalInterface(technicalInterfaceId);
	}

	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

}
