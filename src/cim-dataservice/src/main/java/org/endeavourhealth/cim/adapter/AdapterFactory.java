package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.Registry;

import java.util.ArrayList;

public class AdapterFactory {
    public static IDataAdapter getDataAdapterForService(String odsCode) throws Exception {
        String serviceAdapterTypeName = Registry.getDataAdapterTypeNameForService(odsCode);
        return getAdapter(serviceAdapterTypeName);
    }

    public static ArrayList<IDataAdapter> getAllDataAdapters() throws Exception {
        ArrayList<String> adapterTypes = Registry.getAllAdapterTypes();
        ArrayList<IDataAdapter> adapters = new ArrayList<>();

        for(String adapterTypeName : adapterTypes)
            adapters.add(getAdapter(adapterTypeName));

        return adapters;
    }

    private static IDataAdapter getAdapter(String adapterTypeName) throws Exception {
        try {
            return (IDataAdapter)Class.forName(adapterTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data adapter", e);
        }
    }
}
