package com.minerarcana.transfiguration.parser;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectParser<T extends IObjectBuilder<U>, U> implements IObjectParser<U> {
    private final Supplier<T> constructBuilder;

    private final LinkedList<ObjectParserInfo<T, U, ?>> parserInfos;

    public ObjectParser(Supplier<T> constructBuilder) {
        this.constructBuilder = constructBuilder;
        this.parserInfos = Lists.newLinkedList();
    }

    public <V> ObjectParser<T, U> withField(String field, BiFunction<T, V, T> withBuilder, Function<U, V> getValue,
                                            IObjectParser<V> objectParser) {
        return this.withField(new ObjectParserInfo<T, U, V>(field, withBuilder, getValue, objectParser));
    }

    public <V> ObjectParser<T, U> withField(ObjectParserInfo<T, U, V> parserInfo) {
        this.parserInfos.add(parserInfo);
        return this;
    }

    public U fromJson(@Nullable ResourceLocation resourceLocation, JsonObject jsonObject) throws JsonParseException {
        T builder = constructBuilder.get();
        for (ObjectParserInfo<T, U, ?> parserInfo: parserInfos) {
            builder = parserInfo.fromJson(builder, jsonObject);
        }
        return builder.build(resourceLocation);
    }

    @Override
    public U fromJson(JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonObject()) {
            return fromJson(null, jsonElement.getAsJsonObject());
        } else {
            throw new JsonParseException("Invalid Field Type. Expected 'Object'");
        }
    }

    public JsonElement toJson(U value) {
        JsonObject jsonObject = new JsonObject();
        for (ObjectParserInfo<T, U, ?> parserInfo: parserInfos) {
            jsonObject.add(parserInfo.getFieldName(), parserInfo.toJson(value));
        }
        return jsonObject;
    }

    @Override
    public U fromPacketBuffer(PacketBuffer packetBuffer) {
        return null;
    }

    @Override
    public void toPacketBuffer(U value, PacketBuffer packetBuffer) {

    }
}
