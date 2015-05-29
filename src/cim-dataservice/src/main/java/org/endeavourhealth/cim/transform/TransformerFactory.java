package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.common.ITransformer;

public class TransformerFactory {
    public static ITransformer getTransformerForService(int serviceId) throws Exception {
        String serviceTransformerTypeName = Registry.getTransformerTypeNameForService(serviceId);

        if (serviceTransformerTypeName == null)
            return null;

        try {
            return (ITransformer)Class.forName(serviceTransformerTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data transformer", e);
        }
    }
}
