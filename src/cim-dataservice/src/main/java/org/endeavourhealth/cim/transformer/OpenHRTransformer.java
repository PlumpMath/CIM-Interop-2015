package org.endeavourhealth.cim.transformer;

import org.endeavourhealth.cim.common.ITransformer;

public class OpenHRTransformer implements ITransformer {
    public String toFHIR(String data) {
        return "[Transformed from OpenHR] " + data;
    }
}
