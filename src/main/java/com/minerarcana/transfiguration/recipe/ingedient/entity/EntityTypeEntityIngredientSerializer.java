package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class EntityTypeEntityIngredientSerializer extends EntityIngredientSerializer<EntityTypeEntityIngredient> {
    @Nonnull
    @Override
    public EntityTypeEntityIngredient parse(@Nonnull PacketBuffer buffer) {
        return EntityTypeEntityIngredient.create(buffer.readRegistryId());
    }

    @Nonnull
    @Override
    public EntityTypeEntityIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return EntityTypeEntityIngredient.create(RegistryJson.getEntity(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull EntityTypeEntityIngredient object) {
        buffer.writeRegistryId(object.getEntityType());
    }
}
