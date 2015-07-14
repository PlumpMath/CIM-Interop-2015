package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.Service;
import org.endeavourhealth.cim.InformationSharingFramework.model.SharingAgreement;
import org.endeavourhealth.cim.common.data.GenericRepository;

import java.util.UUID;

public class SharingAgreementRepository extends GenericRepository<SharingAgreement> {

	public SharingAgreementRepository() {
		super(SharingAgreement.class);
	}

	public SharingAgreement getByServiceId(UUID serviceId) {
		SharingAgreement agreement = new SharingAgreement();
		agreement.setName("Sharing Agreement");
		agreement.setId(UUID.randomUUID());
		return agreement;

	}
}
