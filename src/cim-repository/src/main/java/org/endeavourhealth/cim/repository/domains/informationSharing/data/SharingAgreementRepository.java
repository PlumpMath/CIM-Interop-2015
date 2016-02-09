package org.endeavourhealth.cim.repository.domains.informationSharing.data;

import org.endeavourhealth.cim.repository.framework.GenericRepository;
import org.endeavourhealth.cim.repository.domains.informationSharing.model.SharingAgreement;

public class SharingAgreementRepository extends GenericRepository<SharingAgreement>
{

	public SharingAgreementRepository() {
		super(SharingAgreement.class);
	}
}
