package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.dataManager.emis.EmisDataManager;
import org.endeavourhealth.cim.dataManager.emis.TestDataAdapter;

public class EmisTestDataManager extends EmisDataManager implements IDataManager{
	public EmisTestDataManager() {
		_emisDataAdapter = new TestDataAdapter();
	}
}
