package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;

public class ItemResultSerializer extends ResultSerializer<ItemResult> {
    @Nonnull
    @Override
    public ItemResult parse(@Nonnull PacketBuffer buffer) {
        return new ItemResult(buffer.readItemStack());
    }

    @Nonnull
    @Override
    public ItemResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new ItemResult(CraftingHelper.getItemStack(json.getAsJsonObject("itemStack"), true));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull ItemResult object) {
        buffer.writeItemStack(object.getRepresentation());
    }
}
