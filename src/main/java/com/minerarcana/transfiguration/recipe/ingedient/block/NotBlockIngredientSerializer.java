package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.List;

public class NotBlockIngredientSerializer extends BlockIngredientSerializer<NotBlockIngredient> {
    @Nonnull
    @Override
    public NotBlockIngredient parse(@Nonnull PacketBuffer buffer) {
        List<BlockIngredient> blockIngredients = Lists.newArrayList();
        for (int i = 0; i < buffer.readInt(); i++) {
            blockIngredients.add(BlockIngredient.fromBuffer(buffer));
        }
        return new NotBlockIngredient(
                blockIngredients.toArray(new BlockIngredient[0])
        );
    }

    @Nonnull
    @Override
    public NotBlockIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return new NotBlockIngredient(
                SerializerJson.getBlockIngredients(json)
        );
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull NotBlockIngredient object) {
        BlockIngredient[] blockIngredients = object.getBlockIngredients();
        buffer.writeInt(blockIngredients.length);
        for (BlockIngredient blockIngredient : blockIngredients) {
            BlockIngredient.toBuffer(blockIngredient, buffer);
        }
    }
}
