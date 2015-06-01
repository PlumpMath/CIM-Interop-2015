package org.endeavourhealth.cim.transform;

import java.io.IOException;

public class OpenHRTransformer extends TransformerBase {
    public String fromFHIRCareRecord(String fhirData) {
        return null;
    }

    public String toFHIRCareRecord(String nativeData) {
        return "[Transformed from OpenHR] " + nativeData;
    }

    public String fromFHIRCondition(String fhirData) {
        try {
            // Deserialize JSON to fhir condition
            Object fhirCondition = this.fromJSON(fhirData);

            // DO conversion to OpenHR
            Object openHRCondition = null;

            // Serialize to xml and return
            return this.toXML(openHRCondition);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String toFHIRCondition(String nativeData) {
        return null;
    }
}
