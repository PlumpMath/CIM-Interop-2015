package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.Registry;

import java.util.ArrayList;

public class AdapterFactory {
    public static IDataAdapter getDataAdapterForService(String odsCode) throws Exception {
        String serviceAdapterTypeName = Registry.getDataAdapterTypeNameForService(odsCode);

        try {
            return (IDataAdapter)Class.forName(serviceAdapterTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data adapter", e);
        }
    }

    public static ArrayList<IDataAdapter> getAllDataAdapters() throws Exception {
        ArrayList<IDataAdapter> adapters = new ArrayList<>();

        adapters.add(getDataAdapterForService("999"));

        return adapters;
    }
}
