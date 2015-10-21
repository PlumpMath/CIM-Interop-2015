package org.endeavourhealth.common.repository.informationSharing.data;

import org.endeavourhealth.common.repository.informationSharing.model.DatasetCollection;
import org.endeavourhealth.core.repository.common.data.GenericRepository;

public class DatasetCollectionRepository extends GenericRepository<DatasetCollection> {

	public DatasetCollectionRepository() {
		super(DatasetCollection.class);
	}
}
