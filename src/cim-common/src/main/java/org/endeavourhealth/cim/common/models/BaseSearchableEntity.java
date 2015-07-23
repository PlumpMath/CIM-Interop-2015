package org.endeavourhealth.cim.common.models;

import java.util.List;
import java.util.UUID;

public abstract class BaseSearchableEntity extends BaseEntity{
	public abstract String getName();
	public abstract List<EntityIdentifier> getIdentifiers();
}
