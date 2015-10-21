package org.endeavourhealth.core.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {
    public static String serialize(Object entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(entity);
    }

    public static byte[] serializeAsBytes(Object entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsBytes(entity);
    }

    public static <T> T deserialize(Class<T> valueType, String value) throws DeserializationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value, valueType);
        } catch (IOException e) {
            String message = "Could not deserialise to " + valueType.getTypeName();

            throw new DeserializationException(message, e);
        }
    }

    public static <T> T deserialize(Class<T> valueType, byte[] value) throws DeserializationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value, valueType);
        } catch (IOException e) {
            String message = "Could not deserialise to " + valueType.getTypeName();

            throw new DeserializationException(message, e);
        }
    }
}