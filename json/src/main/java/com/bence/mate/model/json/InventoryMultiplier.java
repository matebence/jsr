package com.bence.mate.model.json;

import javax.json.stream.JsonGenerator;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;

public class InventoryMultiplier implements JsonbSerializer<Long> {

    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
        jsonGenerator.write(aLong);
    }
}
