package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.SerializationTags;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BlockTagResultSerializer extends ResultSerializer<BlockTagResult> {
    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new BlockTagResult(SerializationTags.getInstance().getBlocks().getTag(buffer.readResourceLocation()));
    }

    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new BlockTagResult(TagJson.getBlockTag(json));
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull BlockTagResult object) {
        buffer.writeResourceLocation(Objects.requireNonNull(SerializationTags.getInstance().getBlocks()
                .getId(object.getTag())));
    }
}
