package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;

public class ItemResultSerializer extends ResultSerializer<ItemResult> {
    @Nonnull
    @Override
    public ItemResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new ItemResult(buffer.readItem());
    }

    @Nonnull
    @Override
    public ItemResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new ItemResult(CraftingHelper.getItemStack(json.getAsJsonObject("itemStack"), true));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull ItemResult object) {
        buffer.writeItem(object.getRepresentation());
    }
}
