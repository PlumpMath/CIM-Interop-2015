package org.endeavourhealth.cim.common;

public interface ITransformer {
    String fromFHIRCareRecord(String fhirData);
    String toFHIRCareRecord(String nativeData);

    String fromFHIRObservation(String fhirData);
    String toFHIRObservation(String nativeData);
}
