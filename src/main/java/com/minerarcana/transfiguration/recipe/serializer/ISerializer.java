package com.minerarcana.transfiguration.recipe.serializer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface ISerializer<T extends ISerializable<?>> {
    @Nonnull
    T parse(@Nonnull FriendlyByteBuf buffer);

    @Nonnull
    T parse(@Nonnull JsonObject json) throws JsonParseException;

    void write(@Nonnull FriendlyByteBuf buffer, @Nonnull T object);

    @NotNull
    ResourceLocation getKey();
}
