package org.endeavourhealth.cim.repository.informationSharing.data;

import org.endeavourhealth.cim.repository.informationSharing.model.DatasetCollection;
import org.endeavourhealth.cim.common.repository.common.data.GenericRepository;

public class DatasetCollectionRepository extends GenericRepository<DatasetCollection> {

	public DatasetCollectionRepository() {
		super(DatasetCollection.class);
	}
}
