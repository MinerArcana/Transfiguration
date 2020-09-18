package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class TagBlockIngredientSerializer extends BlockIngredientSerializer<TagBlockIngredient> {
    @Override
    @Nonnull
    public TagBlockIngredient parse(@Nonnull PacketBuffer buffer) {
        return TagBlockIngredient.create(buffer.readResourceLocation());
    }

    @Override
    @Nonnull
    public TagBlockIngredient parse(@Nonnull JsonObject json) {
        return new TagBlockIngredient(TagJson.getBlockTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull TagBlockIngredient ingredient) {
        buffer.writeResourceLocation(ingredient.getName());
    }
}
