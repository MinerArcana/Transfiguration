package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class SingleBlockIngredientSerializer extends BlockIngredientSerializer<SingleBlockIngredient> {
    @Nonnull
    @Override
    public SingleBlockIngredient parse(@Nonnull PacketBuffer buffer) {
        return SingleBlockIngredient.create(buffer.readRegistryId());
    }

    @Nonnull
    @Override
    public SingleBlockIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return SingleBlockIngredient.create(RegistryJson.getBlock(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull SingleBlockIngredient object) {
        buffer.writeRegistryId(object.getBlock());
    }
}
