package org.endeavourhealth.core.repository.informationSharing.data;

import org.endeavourhealth.core.repository.informationSharing.model.PublisherProfile;
import org.endeavourhealth.core.repository.common.data.GenericRepository;

public class PublisherProfileRepository extends GenericRepository<PublisherProfile> {

	public PublisherProfileRepository() {
		super(PublisherProfile.class);
	}
}
