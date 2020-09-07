package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

public class BlockStateResultSerializer extends ResultSerializer<BlockStateResult> {
    @Nonnull
    @Override
    public BlockStateResult parse(@Nonnull PacketBuffer buffer) {
        return BlockStateResult.create(buffer.<Block>readRegistryId());
    }

    @Nonnull
    @Override
    public BlockStateResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return BlockStateResult.create(RegistryJson.getBlock(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull BlockStateResult object) {
        buffer.writeRegistryId(object.getBlockState().getBlock());
    }
}
