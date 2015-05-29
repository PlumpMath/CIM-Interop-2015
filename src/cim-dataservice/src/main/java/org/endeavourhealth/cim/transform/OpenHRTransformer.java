package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.common.ITransformer;

public class OpenHRTransformer implements ITransformer {
    public String toCareRecordFHIR(String data) {
        return "[Transformed from OpenHR] " + data;
    }

    public String fromObservationFHIR(Object body) {
        return null;
    }

    public String toObservationFHIR(String response) {
        return null;
    }
}
