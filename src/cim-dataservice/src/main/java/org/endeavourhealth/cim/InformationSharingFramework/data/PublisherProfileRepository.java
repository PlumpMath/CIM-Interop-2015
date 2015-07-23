package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.PublisherProfile;
import org.endeavourhealth.cim.InformationSharingFramework.model.Service;
import org.endeavourhealth.cim.common.data.GenericRepository;

public class PublisherProfileRepository extends GenericRepository<PublisherProfile> {

	public PublisherProfileRepository() {
		super(PublisherProfile.class);
	}
}
