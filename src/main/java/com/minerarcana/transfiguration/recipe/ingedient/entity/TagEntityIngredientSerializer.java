package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.TagCollectionManager;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TagEntityIngredientSerializer extends EntityIngredientSerializer<TagEntityIngredient> {
    @Nonnull
    @Override
    public TagEntityIngredient parse(@Nonnull PacketBuffer buffer) {
        return new TagEntityIngredient(TagCollectionManager.getManager().getEntityTypeTags()
                .get(buffer.readResourceLocation()));
    }

    @Nonnull
    @Override
    public TagEntityIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return new TagEntityIngredient(TagJson.getEntityTypeTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull TagEntityIngredient object) {
        buffer.writeResourceLocation(Objects.requireNonNull(TagCollectionManager.getManager().getEntityTypeTags()
                .getDirectIdFromTag(object.getTag())));
    }
}
