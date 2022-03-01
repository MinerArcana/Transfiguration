package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TagIngredientSerializer extends BasicIngredientSerializer<TagIngredient> {
    @Nonnull
    @Override
    public TagIngredient parse(@Nonnull FriendlyByteBuf buffer) {
        return new TagIngredient(
                getTag(Registry.BLOCK_REGISTRY, buffer),
                getTag(Registry.ENTITY_TYPE_REGISTRY, buffer)
        );
    }

    @Nonnull
    @Override
    public TagIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return new TagIngredient(
                json.has("block") ? TagJson.getBlockTag(json, "block") : null,
                json.has("entity") ? TagJson.getEntityTypeTag(json, "entity") : null
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull TagIngredient object) {
        writeTag(Registry.BLOCK_REGISTRY, buffer, object.getBlockTag());
        writeTag(Registry.ENTITY_TYPE_REGISTRY, buffer, object.getEntityTag());
    }

    private <T extends Registry<U>, U> void writeTag(ResourceKey<T> key, FriendlyByteBuf buffer, Tag<U> tag) {
        buffer.writeBoolean(tag != null);
        if (tag != null) {
            buffer.writeResourceLocation(Objects.requireNonNull(SerializationTags.getInstance()
                    .getOrEmpty(key)
                    .getId(tag)
            ));
        }
    }

    private <T extends Registry<U>, U> Tag<U> getTag(ResourceKey<T> key, FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            return SerializationTags.getInstance()
                    .getOrEmpty(key)
                    .getTag(buffer.readResourceLocation());
        } else {
            return null;
        }
    }
}
