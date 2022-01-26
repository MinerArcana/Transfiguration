package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;

public class EntityResultSerializer extends ResultSerializer<EntityResult> {
    @Nonnull
    @Override
    public EntityResult parse(@Nonnull FriendlyByteBuf buffer) {
        return EntityResult.create(buffer.readRegistryId());
    }

    @Nonnull
    @Override
    public EntityResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return EntityResult.create(RegistryJson.getEntity(json));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull EntityResult object) {
        buffer.writeRegistryId(object.getEntityType());
    }
}
