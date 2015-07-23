package org.endeavourhealth.cim.InformationSharingFramework.data;

import org.endeavourhealth.cim.InformationSharingFramework.model.DatasetCollection;
import org.endeavourhealth.cim.common.data.GenericRepository;

public class DatasetCollectionRepository extends GenericRepository<DatasetCollection> {

	public DatasetCollectionRepository() {
		super(DatasetCollection.class);
	}
}
