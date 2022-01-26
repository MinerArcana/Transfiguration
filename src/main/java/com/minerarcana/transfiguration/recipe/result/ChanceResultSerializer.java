package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nonnull;

public class ChanceResultSerializer extends ResultSerializer<ChanceResult> {
    @Nonnull
    @Override
    public ChanceResult parse(@Nonnull FriendlyByteBuf buffer) {
        return new ChanceResult(
                buffer.readFloat(),
                Result.fromBuffer(buffer)
        );
    }

    @Nonnull
    @Override
    public ChanceResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new ChanceResult(
                GsonHelper.getAsFloat(json, "chance", 1),
                SerializerJson.getResult(json)
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull ChanceResult object) {
        buffer.writeFloat(object.getChance());
        Result.toBuffer(buffer, object.getResult());
    }
}
