package org.endeavourhealth.cim.repository.domains.informationSharing.data;

import org.endeavourhealth.cim.repository.domains.informationSharing.model.Service;
import org.endeavourhealth.cim.repository.framework.GenericRepository;

public class ServiceRepository extends GenericRepository<Service>
{

	public ServiceRepository() {
		super(Service.class);
	}
}
