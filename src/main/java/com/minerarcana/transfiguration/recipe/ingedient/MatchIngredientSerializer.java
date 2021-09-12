package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class MatchIngredientSerializer extends BasicIngredientSerializer<MatchIngredient> {
    @Nonnull
    @Override
    public MatchIngredient parse(@Nonnull PacketBuffer buffer) {
        if (buffer.readBoolean()) {
            return new MatchIngredient(buffer.<Block>readRegistryId());
        } else {
            return new MatchIngredient(buffer.<EntityType<?>>readRegistryId());
        }
    }

    @Nonnull
    @Override
    public MatchIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return null;
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull MatchIngredient object) {

    }
}
