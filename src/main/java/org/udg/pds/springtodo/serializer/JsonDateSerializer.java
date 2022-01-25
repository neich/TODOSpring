package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.udg.pds.springtodo.Global;

import java.io.IOException;
import java.util.Date;

/**
 * Created by imartin on 14/02/17.
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        String formattedDate = Global.dateFormat.format(date);
        gen.writeString(formattedDate);
    }
}
