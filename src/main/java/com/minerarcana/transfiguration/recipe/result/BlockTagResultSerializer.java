package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.TagCollectionManager;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BlockTagResultSerializer extends ResultSerializer<BlockTagResult> {
    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull PacketBuffer buffer) {
        return new BlockTagResult(TagCollectionManager.getManager().getBlockTags().get(buffer.readResourceLocation()));
    }

    @Nonnull
    @Override
    public BlockTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new BlockTagResult(TagJson.getBlockTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull BlockTagResult object) {
        buffer.writeResourceLocation(Objects.requireNonNull(TagCollectionManager.getManager().getBlockTags()
                .getDirectIdFromTag(object.getTag())));
    }
}
