package org.endeavourhealth.cim.repository.domains.informationSharing.model;

import org.endeavourhealth.cim.repository.domains.informationSharing.ISFManager;

import java.util.UUID;

public class System {
	private UUID id;
	private String name;
	private Boolean enabled;
	private UUID technicalInterfaceId;

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

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
}
