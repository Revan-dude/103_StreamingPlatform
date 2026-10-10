package CustomSerializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.Set;
import java.util.stream.Collectors;

public class CustomGenreSerializer extends ValueSerializer<Set<String>> {
    @Override
    public void serialize(Set<String> value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeString(String.join(" - ", value));
    }
}
