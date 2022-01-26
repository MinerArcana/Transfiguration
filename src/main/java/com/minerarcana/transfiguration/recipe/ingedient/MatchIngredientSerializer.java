package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

public class MatchIngredientSerializer extends BasicIngredientSerializer<MatchIngredient> {
    @Nonnull
    @Override
    public MatchIngredient parse(@Nonnull FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            return new MatchIngredient(buffer.<Block>readRegistryId());
        } else {
            return new MatchIngredient(buffer.<EntityType<?>>readRegistryId());
        }
    }

    @Nonnull
    @Override
    public MatchIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        if (json.has("block")) {
            return new MatchIngredient(RegistryJson.getBlock(json));
        } else if (json.has("entity")) {
            return new MatchIngredient(RegistryJson.getEntity(json));
        } else {
            throw new JsonParseException("Match type must have block or entity section");
        }
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull MatchIngredient object) {
        buffer.writeBoolean(object.getBlock() != null);
        if (object.getBlock() != null) {
            buffer.writeRegistryId(object.getBlock());
        } else {
            buffer.writeRegistryId(object.getEntityType());
        }
    }
}
