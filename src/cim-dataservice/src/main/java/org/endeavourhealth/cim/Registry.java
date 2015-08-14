package org.endeavourhealth.cim;

import java.util.*;

public class Registry implements IRegistry {
    private static IRegistry _instance;
	public static IRegistry Instance() {
		if (_instance == null)
			_instance = new Registry();

		return _instance;
	}
	public static void setInstance(IRegistry registry) {
		_instance = registry;
	}

    public String getDataManagerTypeNameForService(String odsCode) throws ClassNotFoundException {
        return "org.endeavourhealth.cim.dataManager.emis.EmisDataManager";
    }

	public ArrayList<String> getAllDataManagerTypes() {
        ArrayList<String> dataManagerTypes = new ArrayList<>();

        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.emis.EmisDataManager");
		// TODO : Move data manager list to db
        /*dataManagerTypes.add("org.endeavourhealth.cim.dataManager.emis.EmisDataManager");
        dataManagerTypes.add("org.endeavourhealth.cim.dataManager.emis.EmisDataManager"); */

        return dataManagerTypes;
    }
}
