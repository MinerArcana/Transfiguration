package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.PushReaction;
import org.apache.commons.lang3.Range;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockPropertiesIngredientSerializer extends BasicIngredientSerializer<BlockPropertiesIngredient> {
    @Nonnull
    @Override
    public BlockPropertiesIngredient parse(@Nonnull FriendlyByteBuf buffer) {
        return new BlockPropertiesIngredient(
                buffer.readBoolean() ? buffer.readBoolean() : null,
                Range.between(buffer.readFloat(), buffer.readFloat()),
                readReactions(buffer)
        );
    }

    private List<PushReaction> readReactions(FriendlyByteBuf packetBuffer) {
        int amount = packetBuffer.readInt();
        List<PushReaction> pushReactions = Lists.newArrayList();
        for (int x = 0; x < amount; x++) {
            pushReactions.add(packetBuffer.readEnum(PushReaction.class));
        }
        return pushReactions;
    }

    @Nonnull
    @Override
    public BlockPropertiesIngredient parse(@Nonnull JsonObject json) throws JsonParseException {
        return new BlockPropertiesIngredient(
                json.has("blockEntity") ? json.get("blockEntity").getAsBoolean() : null,
                ObjectJson.getRange(json, "hardness", JsonElement::getAsFloat),
                ObjectJson.getEnumFromJson(json, "pushReaction", PushReaction.class)
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull BlockPropertiesIngredient object) {
        if (object.getBlockEntity() != null) {
            buffer.writeBoolean(true);
            buffer.writeBoolean(object.getBlockEntity());
        } else {
            buffer.writeBoolean(false);
        }
        buffer.writeFloat(object.getHardness().getMinimum());
        buffer.writeFloat(object.getHardness().getMaximum());
        List<PushReaction> pushReactions = object.getPushReactions();
        buffer.writeInt(pushReactions.size());
        pushReactions.forEach(buffer::writeEnum);
    }
}
