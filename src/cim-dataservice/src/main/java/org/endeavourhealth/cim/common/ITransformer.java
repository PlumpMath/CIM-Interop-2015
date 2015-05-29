package org.endeavourhealth.cim.common;

public interface ITransformer {
    String toCareRecordFHIR(String data);

    String fromObservationFHIR(Object body);
    String toObservationFHIR(String response);
}
