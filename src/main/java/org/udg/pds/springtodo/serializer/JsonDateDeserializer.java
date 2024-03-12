package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * Created by imartin on 14/02/17.
 */
public class JsonDateDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return ZonedDateTime.parse(jsonParser.getValueAsString());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
