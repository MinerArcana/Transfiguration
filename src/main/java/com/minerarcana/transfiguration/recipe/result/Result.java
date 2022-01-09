package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public abstract class Result implements ISerializable<ResultSerializer<?>> {

    @Nonnull
    public abstract ResultInstance create();

    @Nonnull
    public abstract ItemStack getRepresentation();

    public static Result fromBuffer(PacketBuffer buffer) {
        ResultSerializer<?> serializer = buffer.readRegistryId();
        return serializer.parse(buffer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Result> void toBuffer(PacketBuffer buffer, T result) {
        ResultSerializer<T> resultSerializer = (ResultSerializer<T>) result.getSerializer();
        buffer.writeRegistryId(resultSerializer);
        resultSerializer.write(buffer, result);
    }
}
