package org.endeavourhealth.core.repository.informationSharing.data;

import org.endeavourhealth.core.repository.informationSharing.model.Service;
import org.endeavourhealth.core.repository.common.data.GenericRepository;

public class ServiceRepository extends GenericRepository<Service> {

	public ServiceRepository() {
		super(Service.class);
	}
}
