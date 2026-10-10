package CustomSerializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomGenreDeserializer extends ValueDeserializer<Set<String>> {
    @Override
    public Set<String> deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {

        String[] str = p.readValueAs(String.class).split(" - ");

        return new LinkedHashSet<>(Arrays.asList(str));
    }
}
