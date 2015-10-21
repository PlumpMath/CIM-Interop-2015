package org.endeavourhealth.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.endeavourhealth.core.repository.common.model.PartialDateTime;

import java.io.IOException;

public class PartialDateTimeDeserializer extends JsonDeserializer<PartialDateTime> {

    @Override
    public PartialDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() != JsonToken.VALUE_STRING) {
            throw deserializationContext.mappingException("Expected JSON String");
        }
        return new PartialDateTime(jsonParser.getValueAsString());
    }
}
