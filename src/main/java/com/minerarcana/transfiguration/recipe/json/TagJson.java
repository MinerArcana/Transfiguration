package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class TagJson {
    public static Tag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject, String fieldName) {
        String tagName = GsonHelper.getAsString(jsonObject, fieldName);
        Tag<EntityType<?>> entityTypeITag = SerializationTags.getInstance()
                .getOrEmpty(Registry.ENTITY_TYPE_REGISTRY)
                .getTag(new ResourceLocation(tagName));
        if (entityTypeITag != null) {
            return entityTypeITag;
        } else {
            throw new JsonParseException("Failed ot find Entity Tag for " + tagName);
        }
    }

    public static Tag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject) {
        return getEntityTypeTag(jsonObject, "tag");
    }

    public static Tag<Block> getBlockTag(JsonObject jsonObject, String fieldName) {
        String tagName = GsonHelper.getAsString(jsonObject, fieldName);
        Tag<Block> blockITag = SerializationTags.getInstance()
                .getOrEmpty(Registry.BLOCK_REGISTRY)
                .getTag(new ResourceLocation(tagName));
        if (blockITag != null) {
            return blockITag;
        } else {
            throw new JsonParseException("Failed to find Block Tag for " + tagName);
        }
    }

    public static Tag<Block> getBlockTag(JsonObject jsonObject) {
        return getBlockTag(jsonObject, "tag");
    }
}
