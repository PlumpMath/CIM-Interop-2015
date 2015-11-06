package org.endeavourhealth.core.repository.informationSharingProtocols;

import java.util.UUID;

public class InformationSharingProtocol {
	private UUID id;
	private String data;

	private String dataSchemaVersion;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDataSchemaVersion() {
		return dataSchemaVersion;
	}

	public void setDataSchemaVersion(String dataSchemaVersion) {
		this.dataSchemaVersion = dataSchemaVersion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
