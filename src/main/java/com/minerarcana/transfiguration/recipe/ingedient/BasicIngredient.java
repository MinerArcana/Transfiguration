package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public abstract class BasicIngredient implements ISerializable<BasicIngredientSerializer<?>> {
    public boolean test(@Nonnull BlockState blockState) {
        return false;
    }

    public boolean test(@Nonnull Entity entity) {
        return false;
    }

    public static BasicIngredient fromBuffer(PacketBuffer packetBuffer) {
        BasicIngredientSerializer<?> serializer = packetBuffer.readRegistryId();
        return serializer.parse(packetBuffer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BasicIngredient> void toBuffer(PacketBuffer packetBuffer, T basicIngredient) {
        BasicIngredientSerializer<T> blockIngredientSerializer = (BasicIngredientSerializer<T>) basicIngredient.getSerializer();
        packetBuffer.writeRegistryId(blockIngredientSerializer);
        blockIngredientSerializer.write(packetBuffer, basicIngredient);
    }
}
