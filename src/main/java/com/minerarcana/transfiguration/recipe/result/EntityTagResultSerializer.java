package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;

import javax.annotation.Nonnull;

public class EntityTagResultSerializer extends ResultSerializer<EntityTagResult> {
    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new EntityTagResult(
                TagKey.create(Registry.ENTITY_TYPE_REGISTRY, buffer.readResourceLocation())
        );
    }

    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new EntityTagResult(
                TagJson.getEntityTypeTag(json)
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull EntityTagResult object) {
        buffer.writeResourceLocation(object.getTag().location());
    }
}
