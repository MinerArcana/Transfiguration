package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.RecipeResult;
import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;

public class RecipeResultObjectParser implements IObjectParser<RecipeResult> {
    public static final RecipeResultObjectParser INSTANCE = new RecipeResultObjectParser();

    @Override
    public RecipeResult fromJson(JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonObject()) {
            Block block = ForgeRegisteringParsing.blockFromJson("block", jsonElement.getAsJsonObject());
            return new RecipeResult(block.getDefaultState());
        } else {
            throw new JsonParseException("Failed to find Object");
        }
    }

    @Override
    public JsonElement toJson(RecipeResult value) {
        return null;
    }

    @Override
    public RecipeResult fromPacketBuffer(PacketBuffer packetBuffer) {
        return null;
    }

    @Override
    public void toPacketBuffer(RecipeResult value, PacketBuffer packetBuffer) {

    }
}
