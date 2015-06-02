package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.Registry;

public class AdapterFactory {
    public static IDataAdapter getDataAdapterForService(String serviceId) throws Exception {
        String serviceAdapterTypeName = Registry.getDataAdapterTypeNameForService(serviceId);

        try {
            return (IDataAdapter)Class.forName(serviceAdapterTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data adapter", e);
        }
    }
}
