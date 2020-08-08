package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.minecraft.network.PacketBuffer;

import java.util.function.Function;

public class FunctionObjectParser<T> implements IObjectParser<T> {
    private final Function<String, T> toValue;
    private final Function<T, String> toName;

    public FunctionObjectParser(Function<String, T> toValue, Function<T, String> toName) {
        this.toValue = toValue;
        this.toName = toName;
    }

    @Override
    public T fromJson(JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            return toValue.apply(jsonElement.getAsString());
        } else {
            throw new JsonParseException("Expected String");
        }
    }

    @Override
    public JsonElement toJson(T value) {
        return new JsonPrimitive(toName.apply(value));
    }

    @Override
    public T fromPacketBuffer(PacketBuffer packetBuffer) {
        return null;
    }

    @Override
    public void toPacketBuffer(T value, PacketBuffer packetBuffer) {

    }
}
