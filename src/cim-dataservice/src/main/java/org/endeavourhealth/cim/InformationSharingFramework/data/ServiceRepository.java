package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.Service;
import org.endeavourhealth.cim.InformationSharingFramework.model.SharingAgreement;
import org.endeavourhealth.cim.common.data.GenericRepository;

import java.util.UUID;

public class ServiceRepository extends GenericRepository<Service> {

	public ServiceRepository() {
		super(Service.class);
	}

	public Service getByOdsCode(String OdsCode) {
		Service publisherService = new Service();
		publisherService.setName("Publisher Service");
		publisherService.setId(UUID.randomUUID());
		return publisherService;
	}

	public Service getByPublicApiKey(String subscriberApiKey) {
		Service subscriberService = new Service();
		subscriberService.setName("Subscriber Service");
		subscriberService.setId(UUID.randomUUID());
		return subscriberService;
	}
}
