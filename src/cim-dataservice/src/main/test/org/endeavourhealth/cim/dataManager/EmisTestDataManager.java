package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.adapter.TestDataAdapter;

public class EmisTestDataManager extends EmisDataManager implements IDataManager{
	public EmisTestDataManager() {
		_emisDataAdapter = new TestDataAdapter();
	}
}
