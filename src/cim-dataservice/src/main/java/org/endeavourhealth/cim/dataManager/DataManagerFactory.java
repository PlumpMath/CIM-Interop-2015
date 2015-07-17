package org.endeavourhealth.cim.dataManager;

import org.endeavourhealth.cim.Registry;

import java.util.ArrayList;

public class DataManagerFactory {
    public static IDataManager getDataManagerForService(String odsCode) throws Exception {
        String dataManagerTypeName = Registry.Instance().getDataManagerTypeNameForService(odsCode);
        return getDataManager(dataManagerTypeName);
    }

    public static ArrayList<IDataManager> getAllDataAdapters() throws Exception {
        ArrayList<String> dataManagerTypes = Registry.Instance().getAllDataManagerTypes();
        ArrayList<IDataManager> dataManagers = new ArrayList<>();

        for(String dataManagerTypeName : dataManagerTypes)
            dataManagers.add(getDataManager(dataManagerTypeName));

        return dataManagers;
    }

    private static IDataManager getDataManager(String dataManagerTypeName) throws Exception {
        try {
            return (IDataManager)Class.forName(dataManagerTypeName).newInstance();
        }
        catch (Exception e) {
            throw new ClassNotFoundException("Could not load data manager", e);
        }
    }
}
