package org.endeavourhealth.cim.adapter;

import org.endeavourhealth.cim.IRegistry;
import org.endeavourhealth.cim.Registry;

import java.util.ArrayList;

public class AdapterFactory {
	private static IRegistry _registry = new Registry();

    public static IDataAdapter getDataAdapterForService(String odsCode) throws Exception {
        String serviceAdapterTypeName = _registry.getDataAdapterTypeNameForService(odsCode);
        return getAdapter(serviceAdapterTypeName);
    }

    public static ArrayList<IDataAdapter> getAllDataAdapters() throws Exception {
        ArrayList<String> adapterTypes = _registry.getAllAdapterTypes();
        ArrayList<IDataAdapter> adapters = new ArrayList<>();

        for(String adapterTypeName : adapterTypes)
            adapters.add(getAdapter(adapterTypeName));

        return adapters;
    }

	public static void setRegistry(IRegistry registry) {
		_registry = registry;
	}

    private static IDataAdapter getAdapter(String adapterTypeName) throws Exception {
        try {
            return (IDataAdapter)Class.forName(adapterTypeName).newInstance();
        }
        catch (Exception e) {
            throw new ClassNotFoundException("Could not load data adapter", e);
        }
    }
}
