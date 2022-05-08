package com.bence.mate.model.json;

import java.lang.reflect.Type;
import javax.json.stream.JsonParser;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.DeserializationContext;

public class InventoryDivider implements JsonbDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
        return jsonParser.getLong();
    }
}
