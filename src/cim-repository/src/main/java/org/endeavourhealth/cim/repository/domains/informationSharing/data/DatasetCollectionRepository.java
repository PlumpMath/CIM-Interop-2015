package org.endeavourhealth.cim.repository.domains.informationSharing.data;

import org.endeavourhealth.cim.repository.framework.GenericRepository;
import org.endeavourhealth.cim.repository.domains.informationSharing.model.DatasetCollection;

public class DatasetCollectionRepository extends GenericRepository<DatasetCollection>
{

	public DatasetCollectionRepository() {
		super(DatasetCollection.class);
	}
}
