package org.endeavourhealth.cim.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.endeavourhealth.cim.common.repository.common.model.PartialDateTime;

import java.io.IOException;

public class PartialDateTimeSerializer extends com.fasterxml.jackson.databind.JsonSerializer<PartialDateTime> {
    @Override
    public void serialize(PartialDateTime partialDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(partialDateTime.asString());
    }
}