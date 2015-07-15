package org.endeavourhealth.cim.InformationSharingFramework.model;

import org.endeavourhealth.cim.InformationSharingFramework.ISFManager;

public class System {
	private Integer id;
	private String name;
	private Boolean enabled;
	private Integer technicalInterfaceId;

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

	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getTechnicalInterfaceId() {
		return technicalInterfaceId;
	}
	public void setTechnicalInterfaceId(Integer technicalInterfaceId) {
		this.technicalInterfaceId = technicalInterfaceId;
	}
	public TechnicalInterface getTechnicalInterface() {
		return ISFManager.Instance().getTechnicalInterface(technicalInterfaceId);
	}
}
