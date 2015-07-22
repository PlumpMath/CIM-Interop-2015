package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.SharingAgreement;
import org.endeavourhealth.cim.common.data.GenericRepository;

public class SharingAgreementRepository extends GenericRepository<SharingAgreement> {

	public SharingAgreementRepository() {
		super(SharingAgreement.class);
	}
}
