package org.endeavourhealth.core.repository.informationSharing.data;

import org.endeavourhealth.core.repository.informationSharing.model.DatasetCollection;
import org.endeavourhealth.core.repository.common.data.GenericRepository;

public class DatasetCollectionRepository extends GenericRepository<DatasetCollection> {

	public DatasetCollectionRepository() {
		super(DatasetCollection.class);
	}
}
