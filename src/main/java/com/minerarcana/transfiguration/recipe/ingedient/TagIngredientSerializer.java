package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class TagIngredientSerializer extends BasicIngredientSerializer<TagIngredient> {
    @Nonnull
    @Override
    public TagIngredient parse(@Nonnull PacketBuffer buffer) {
        return new TagIngredient(
                getTag(ITagCollectionSupplier::getBlockTags, buffer),
                getTag(ITagCollectionSupplier::getEntityTypeTags, buffer)
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
    public void write(@Nonnull PacketBuffer buffer, @Nonnull TagIngredient object) {
        writeTag(ITagCollectionSupplier::getBlockTags, buffer, object.getBlockTag());
        writeTag(ITagCollectionSupplier::getEntityTypeTags, buffer, object.getEntityTag());
    }

    private <T> void writeTag(Function<ITagCollectionSupplier, ITagCollection<T>> getTagCol, PacketBuffer buffer, ITag<T> tag) {
        buffer.writeBoolean(tag != null);
        if (tag != null) {
            buffer.writeString(getTagCol.apply(TagCollectionManager.getManager()).getValidatedIdFromTag(tag).toString());
        }
    }

    private <T> ITag<T> getTag(Function<ITagCollectionSupplier, ITagCollection<T>> getTagCol, PacketBuffer buffer) {
        if (buffer.readBoolean()) {
            return getTagCol.apply(TagCollectionManager.getManager())
                    .get(new ResourceLocation(buffer.readString()));
        } else {
            return null;
        }
    }
}
