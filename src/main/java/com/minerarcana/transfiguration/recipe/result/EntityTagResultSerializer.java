package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.SerializationTags;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityTagResultSerializer extends ResultSerializer<EntityTagResult> {
    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new EntityTagResult(
                SerializationTags.getInstance()
                        .getOrEmpty(Registry.ENTITY_TYPE_REGISTRY)
                        .getTag(buffer.readResourceLocation())
        );
    }

    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new EntityTagResult(TagJson.getEntityTypeTag(json));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull EntityTagResult object) {
        buffer.writeResourceLocation(Objects.requireNonNull(
                SerializationTags.getInstance()
                        .getOrEmpty(Registry.ENTITY_TYPE_REGISTRY)
                        .getId(object.getTag())
        ));
    }
}
