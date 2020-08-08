package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.ingedient.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.SingleBlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.TagBlockIngredient;
import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockIngredientObjectParser implements IObjectParser<BlockIngredient>  {
    public static final BlockIngredientObjectParser INSTANCE = new BlockIngredientObjectParser();

    @Override
    public BlockIngredient fromJson(JsonElement jsonElement) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            return this.getFromString(jsonElement);
        } else if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("tag")) {
                ITag<Block> tag = TagCollectionManager.func_232928_e_()
                        .func_232923_a_()
                        .get(new ResourceLocation(jsonObject.get("tag").getAsString()));
                if (tag != null) {
                    return new TagBlockIngredient(tag);
                } else {
                    throw new JsonParseException("Failed to find Tag '" + jsonObject.get("tag").getAsString());
                }
            } else if (jsonObject.has("block")) {
                return getFromString(jsonObject.get("block"));
            }
        }
        throw new JsonParseException("Failed to find String or Object");
    }

    private BlockIngredient getFromString(JsonElement jsonElement) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(jsonElement.getAsString()));
        if (block == null) {
            throw new JsonParseException("Failed to find Block for '" + jsonElement.getAsString() + "'");
        } else {
            return new SingleBlockIngredient(block);
        }
    }

    @Override
    public JsonElement toJson(BlockIngredient value) {
        return null;
    }

    @Override
    public BlockIngredient fromPacketBuffer(PacketBuffer packetBuffer) {
        return null;
    }

    @Override
    public void toPacketBuffer(BlockIngredient value, PacketBuffer packetBuffer) {

    }
}
