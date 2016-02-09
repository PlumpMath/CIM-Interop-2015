package org.endeavourhealth.cim.repository.domains.informationSharing.data;

import org.endeavourhealth.cim.repository.domains.informationSharing.model.PublisherProfile;
import org.endeavourhealth.cim.repository.framework.GenericRepository;

public class PublisherProfileRepository extends GenericRepository<PublisherProfile>
{

	public PublisherProfileRepository() {
		super(PublisherProfile.class);
	}
}
