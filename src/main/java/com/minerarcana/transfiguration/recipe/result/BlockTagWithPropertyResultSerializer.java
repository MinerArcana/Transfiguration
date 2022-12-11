package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockTagWithPropertyResultSerializer extends ResultSerializer<BlockTagWithPropertyResult> {
    @Nonnull
    @Override
    public BlockTagWithPropertyResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new BlockTagWithPropertyResult(
                TagKey.create(Registry.BLOCK_REGISTRY, buffer.readResourceLocation()),
                buffer.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf)
        );
    }

    @Nonnull
    @Override
    public BlockTagWithPropertyResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new BlockTagWithPropertyResult(
                TagJson.getBlockTag(json),
                GsonHelper.getAsJsonObject(json, "properties", new JsonObject())
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().toString()
                        ))
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull BlockTagWithPropertyResult object) {
        buffer.writeResourceLocation(object.getTag().location());
        buffer.writeMap(object.getProperties(), FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
    }

}
