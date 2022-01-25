package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.udg.pds.springtodo.Global;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by imartin on 14/02/17.
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return Global.dateFormat.parse(jsonParser.getValueAsString());
        } catch (ParseException e) {
            return null;
        }
    }
}
