package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.Service;
import org.endeavourhealth.cim.InformationSharingFramework.model.SharingAgreement;
import org.endeavourhealth.cim.common.data.GenericRepository;

import java.util.UUID;

public class ServiceRepository extends GenericRepository<Service> {

	public ServiceRepository() {
		super(Service.class);
	}
}
