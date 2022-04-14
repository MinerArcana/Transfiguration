package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;

import javax.annotation.Nonnull;

public class BlockTagResultSerializer extends ResultSerializer<BlockTagResult> {
    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new BlockTagResult(
                TagKey.create(Registry.BLOCK_REGISTRY, buffer.readResourceLocation())
        );
    }

    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new BlockTagResult(TagJson.getBlockTag(json));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull BlockTagResult object) {
        buffer.writeResourceLocation(object.getTag().location());
    }
}
