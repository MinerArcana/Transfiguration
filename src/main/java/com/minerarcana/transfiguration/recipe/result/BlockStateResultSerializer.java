package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class BlockStateResultSerializer extends ResultSerializer<BlockStateResult> {
    @Nonnull
    @Override
    public BlockStateResult parse(@Nonnull FriendlyByteBuf buffer) {
        return BlockStateResult.create(buffer.<Block>readRegistryId());
    }

    @Nonnull
    @Override
    public BlockStateResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return BlockStateResult.create(RegistryJson.getBlock(json));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull BlockStateResult object) {
        buffer.writeRegistryId(ForgeRegistries.BLOCKS, object.getBlockState().getBlock());
    }
}
