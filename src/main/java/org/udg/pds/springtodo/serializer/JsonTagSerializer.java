package org.udg.pds.springtodo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Views;

import java.io.IOException;

/**
 * Created by imartin on 14/02/17.
 */
public class JsonTagSerializer extends JsonSerializer<Tag> {

    @Override
    public void serialize(Tag tag, JsonGenerator gen, SerializerProvider provider)
        throws IOException, JsonProcessingException {
        gen.writeString(tag.getName());
    }
}
