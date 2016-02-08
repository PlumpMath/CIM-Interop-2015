package org.endeavourhealth.cim.transform;

public interface IRecordTransformer {
	String toFhirCareRecord(String nativeData) throws Exception;
	String fromFhirCareRecord(String fhirData);
}
