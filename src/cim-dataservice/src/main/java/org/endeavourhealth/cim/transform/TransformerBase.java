package org.endeavourhealth.cim.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public abstract class TransformerBase {
    protected static String toJSON(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        return json;
    }
    protected static Object fromJSON(String string) throws IOException {
        Object object = new ObjectMapper().readValue(string, Object.class);
        return object;
    }

    protected static String toXML(Object object) throws JsonProcessingException {
        // XML serializer implementation
        return null;
    }
    protected static Object fromXML(String string) throws IOException {
        // XML deserializer implementation
        return null;
    }

    public abstract String fromFHIRCareRecord(String fhirData);
    public abstract String toFHIRCareRecord(String nativeData);

    public abstract String fromFHIRCondition(String fhirData);
    public abstract String toFHIRCondition(String nativeData);
}
