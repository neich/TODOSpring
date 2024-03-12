package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.udg.pds.springtodo.Global;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * Created by imartin on 14/02/17.
 */
public class JsonDateSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime date, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        String formattedDate = Global.AppDateFormatter.format(date);
        gen.writeString(formattedDate);
    }
}
