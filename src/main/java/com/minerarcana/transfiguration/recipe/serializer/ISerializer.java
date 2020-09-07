package com.minerarcana.transfiguration.recipe.serializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public interface ISerializer<T extends ISerializable<?>> {
    @Nonnull
    T parse(@Nonnull PacketBuffer buffer);

    @Nonnull
    T parse(@Nonnull JsonObject json) throws JsonParseException;

    void write(@Nonnull PacketBuffer buffer, @Nonnull T object);
}
