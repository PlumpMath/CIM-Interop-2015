package org.endeavourhealth.cim.common.models;

import java.util.List;
import java.util.UUID;

public abstract class BaseEntity {
	public abstract UUID getId();
	public abstract String getName();
	public abstract String getSchemaVersion();
	public abstract List<EntityIdentifier> getIdentifiers();
}
