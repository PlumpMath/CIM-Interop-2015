package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.InformationSharingFramework.Manager;
import org.endeavourhealth.cim.common.models.BaseEntity;
import org.endeavourhealth.cim.common.models.EntityIdentifier;

import java.util.List;
import java.util.UUID;

public class SharingAgreement extends BaseEntity {
	private UUID id;
	private String name;
	private List<EntityIdentifier> identifiers;
	private Integer serviceId;
	private Integer technicalInterfaceId;
	private Boolean active;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSchemaVersion() { return "1.0"; }

	public List<EntityIdentifier> getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(List<EntityIdentifier> identifiers) { this.identifiers = identifiers; }

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
