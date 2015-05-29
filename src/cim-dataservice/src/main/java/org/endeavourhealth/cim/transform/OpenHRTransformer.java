package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.common.ITransformer;

public class OpenHRTransformer implements ITransformer {
    public String fromFHIRCareRecord(String fhirData) {
        return null;
    }

    public String toFHIRCareRecord(String nativeData) {
        return "[Transformed from OpenHR] " + nativeData;
    }

    public String fromFHIRObservation(String fhirData) {
        return null;
    }

    public String toFHIRObservation(String nativeData) {
        return null;
    }
}
