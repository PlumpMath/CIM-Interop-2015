package org.endeavourhealth.cim.repository.informationSharing.data;

import org.endeavourhealth.cim.repository.informationSharing.model.PublisherProfile;
import org.endeavourhealth.cim.common.data.GenericRepository;

public class PublisherProfileRepository extends GenericRepository<PublisherProfile> {

	public PublisherProfileRepository() {
		super(PublisherProfile.class);
	}
}
