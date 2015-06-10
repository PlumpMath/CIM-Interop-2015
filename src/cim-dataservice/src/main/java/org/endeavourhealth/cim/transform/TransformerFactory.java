package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.adapter.IDataAdapter;

public class TransformerFactory {
    public static Transformer getTransformerForAdapter(IDataAdapter dataAdapter) throws Exception {
        String serviceTransformerTypeName = dataAdapter.getTransformerTypeName();

        if (serviceTransformerTypeName == null)
            return null;

        try {
            return (Transformer)Class.forName(serviceTransformerTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data transformer", e);
        }
    }
}
