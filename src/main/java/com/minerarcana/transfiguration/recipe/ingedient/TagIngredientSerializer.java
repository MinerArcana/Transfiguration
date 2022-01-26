package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tags.TagContainer;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class TagIngredientSerializer extends BasicIngredientSerializer<TagIngredient> {
    @Nonnull
    @Override
    public TagIngredient parse(@Nonnull FriendlyByteBuf buffer) {
        return new TagIngredient(
                getTag(TagContainer::getBlocks, buffer),
                getTag(TagContainer::getEntityTypes, buffer)
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
        writeTag(TagContainer::getBlocks, buffer, object.getBlockTag());
        writeTag(TagContainer::getEntityTypes, buffer, object.getEntityTag());
    }

    private <T> void writeTag(Function<TagContainer, TagCollection<T>> getTagCol, FriendlyByteBuf buffer, Tag<T> tag) {
        buffer.writeBoolean(tag != null);
        if (tag != null) {
            buffer.writeUtf(getTagCol.apply(SerializationTags.getInstance()).getIdOrThrow(tag).toString());
        }
    }

    private <T> Tag<T> getTag(Function<TagContainer, TagCollection<T>> getTagCol, FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            return getTagCol.apply(SerializationTags.getInstance())
                    .getTag(new ResourceLocation(buffer.readUtf()));
        } else {
            return null;
        }
    }
}
