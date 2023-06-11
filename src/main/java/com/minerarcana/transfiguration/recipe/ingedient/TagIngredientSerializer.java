package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import javax.annotation.Nonnull;

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
        writeTag(buffer, object.getBlockTag());
        writeTag(buffer, object.getEntityTag());
    }

    private <U> void writeTag(FriendlyByteBuf buffer, TagKey<U> tag) {
        buffer.writeBoolean(tag != null);
        if (tag != null) {
            buffer.writeResourceLocation(tag.location());
        }
    }

    private <T extends Registry<U>, U> TagKey<U> getTag(ResourceKey<T> key, FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            return TagKey.create(key, buffer.readResourceLocation());
        } else {
            return null;
        }
    }
}
