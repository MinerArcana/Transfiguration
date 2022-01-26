package com.minerarcana.transfiguration.recipe.serializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;

public interface ISerializer<T extends ISerializable<?>> {
    @Nonnull
    T parse(@Nonnull FriendlyByteBuf buffer);

    @Nonnull
    T parse(@Nonnull JsonObject json) throws JsonParseException;

    void write(@Nonnull FriendlyByteBuf buffer, @Nonnull T object);
}
