package org.endeavourhealth.transform;

public interface IRecordTransformer {
	String toFhirCareRecord(String nativeData) throws Exception;
	String fromFhirCareRecord(String fhirData);
}
