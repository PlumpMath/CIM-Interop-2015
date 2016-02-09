package org.endeavourhealth.cim.repository.framework;

import java.util.UUID;

public abstract class BaseEntity {
	private UUID id;
	public abstract String getSchemaVersion();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
