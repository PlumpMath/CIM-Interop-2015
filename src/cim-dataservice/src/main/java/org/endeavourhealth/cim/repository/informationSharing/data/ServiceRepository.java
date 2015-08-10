package org.endeavourhealth.cim.repository.informationSharing.data;

import org.endeavourhealth.cim.repository.informationSharing.model.Service;
import org.endeavourhealth.cim.common.repository.common.data.GenericRepository;

public class ServiceRepository extends GenericRepository<Service> {

	public ServiceRepository() {
		super(Service.class);
	}
}
