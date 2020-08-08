package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;

public interface IObjectParser<T> {
    T fromJson(JsonElement jsonElement) throws JsonParseException;

    JsonElement toJson(T value);

    T fromPacketBuffer(PacketBuffer packetBuffer);

    void toPacketBuffer(T value, PacketBuffer packetBuffer);
}
