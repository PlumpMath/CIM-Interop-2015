package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.IDataAdapter;

public class AdapterFactory {
    public static IDataAdapter getDataAdapterForService(int serviceId) throws Exception {
        String serviceAdapterTypeName = Registry.getDataAdapterTypeNameForService(serviceId);

        try {
            return (IDataAdapter)Class.forName(serviceAdapterTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data adapter", e);
        }
    }
}
