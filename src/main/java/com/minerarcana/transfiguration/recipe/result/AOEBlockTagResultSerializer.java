package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.TagCollectionManager;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AOEBlockTagResultSerializer extends ResultSerializer<AOEBlockTagResult> {
    @Nonnull
    @Override
    public AOEBlockTagResult parse(@Nonnull PacketBuffer buffer) {
        return new AOEBlockTagResult(TagCollectionManager.getManager().getBlockTags().get(buffer.readResourceLocation()));
    }

    @Nonnull
    @Override
    public AOEBlockTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new AOEBlockTagResult(TagJson.getBlockTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull AOEBlockTagResult object) {
        buffer.writeResourceLocation(Objects.requireNonNull(TagCollectionManager.getManager().getBlockTags()
                .getDirectIdFromTag(object.getTag())));
    }
}
