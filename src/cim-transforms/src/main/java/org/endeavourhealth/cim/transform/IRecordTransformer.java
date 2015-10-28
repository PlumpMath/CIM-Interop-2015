package org.endeavourhealth.cim.transform;

public interface IRecordTransformer {
	String toFhirCareRecord(String nativeData);
	String fromFhirCareRecord(String fhirData);
}
