package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.Registry;

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
}
