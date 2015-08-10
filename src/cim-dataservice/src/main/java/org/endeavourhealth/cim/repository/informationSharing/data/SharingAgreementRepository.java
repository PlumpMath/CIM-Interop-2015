package org.endeavourhealth.cim.repository.informationSharing.data;

import org.endeavourhealth.cim.repository.informationSharing.model.SharingAgreement;
import org.endeavourhealth.cim.common.repository.common.data.GenericRepository;

public class SharingAgreementRepository extends GenericRepository<SharingAgreement> {

	public SharingAgreementRepository() {
		super(SharingAgreement.class);
	}
}
