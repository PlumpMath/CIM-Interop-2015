package org.endeavourhealth.core.repository.informationSharing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AgreementByService {
	private UUID id;
	private ArrayList<UUID> agreements = new ArrayList<>();

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public String getSchemaVersion() { return "1.0"; }

	public List<UUID> getAgreements() {
		return agreements;
	}
}
