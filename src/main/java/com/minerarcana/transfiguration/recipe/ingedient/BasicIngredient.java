package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public abstract class BasicIngredient implements ISerializable<BasicIngredientSerializer<?>> {
    public boolean test(@Nonnull BlockState blockState) {
        return false;
    }

    public boolean test(@Nonnull Entity entity) {
        return false;
    }

    public static BasicIngredient fromBuffer(FriendlyByteBuf packetBuffer) {
        BasicIngredientSerializer<?> serializer = packetBuffer.readRegistryId();
        return serializer.parse(packetBuffer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BasicIngredient> void toBuffer(FriendlyByteBuf packetBuffer, T basicIngredient) {
        BasicIngredientSerializer<T> blockIngredientSerializer = (BasicIngredientSerializer<T>) basicIngredient.getSerializer();
        packetBuffer.writeRegistryId(TransfigurationRecipes.getIngredientRegistry(), blockIngredientSerializer);
        blockIngredientSerializer.write(packetBuffer, basicIngredient);
    }

    public Ingredient asItemIngredient() {
        return Ingredient.EMPTY;
    }
}
