package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
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
        JsonPrimitive tagName = json.getAsJsonPrimitive("tag");
        if (tagName != null) {
            return TagBlockIngredient.create(tagName.getAsString());
        } else {
            throw new JsonParseException("Failed to find 'tag'");
        }
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull TagBlockIngredient ingredient) {
        buffer.writeResourceLocation(ingredient.getName());
    }
}
