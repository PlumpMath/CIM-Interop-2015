package org.endeavourhealth.cim.common.repository.common.model;

import java.util.List;

public abstract class BaseSearchableEntity extends BaseEntity{
	public abstract String getName();
	public abstract List<EntityIdentifier> getIdentifiers();
}
