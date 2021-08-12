package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

import javax.annotation.Nonnull;

public class ChanceResultSerializer extends ResultSerializer<ChanceResult> {
    @Nonnull
    @Override
    public ChanceResult parse(@Nonnull PacketBuffer buffer) {
        return new ChanceResult(
                buffer.readFloat(),
                Result.fromBuffer(buffer)
        );
    }

    @Nonnull
    @Override
    public ChanceResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new ChanceResult(
                JSONUtils.getFloat(json, "chance", 1),
                SerializerJson.getResult(json)
        );
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull ChanceResult object) {
        buffer.writeFloat(object.getChance());
        Result.toBuffer(buffer, object.getResult());
    }
}
