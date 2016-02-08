package org.endeavourhealth.common.repository.informationSharing.data;

import org.endeavourhealth.common.repository.informationSharing.model.SharingAgreement;
import org.endeavourhealth.core.repository.common.data.GenericRepository;

public class SharingAgreementRepository extends GenericRepository<SharingAgreement> {

	public SharingAgreementRepository() {
		super(SharingAgreement.class);
	}
}
