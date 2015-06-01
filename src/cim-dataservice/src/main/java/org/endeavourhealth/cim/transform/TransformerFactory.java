package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.Registry;

public class TransformerFactory {
    public static TransformerBase getTransformerForService(String serviceId) throws Exception {
        String serviceTransformerTypeName = Registry.getTransformerTypeNameForService(serviceId);

        if (serviceTransformerTypeName == null)
            return null;

        try {
            return (TransformerBase)Class.forName(serviceTransformerTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data transformer", e);
        }
    }
}
