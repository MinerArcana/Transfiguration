package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TagJson {
    public static ITag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject, String fieldName) {
        String tagName = JSONUtils.getString(jsonObject, fieldName);
        ITag<EntityType<?>> entityTypeITag = TagCollectionManager.getManager().getEntityTypeTags().get(new ResourceLocation(tagName));
        if (entityTypeITag != null) {
            return entityTypeITag;
        } else {
            throw new JsonParseException("Failed ot find Entity Tag for " + tagName);
        }
    }

    public static ITag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject) {
        return getEntityTypeTag(jsonObject, "tag");
    }

    public static ITag<Block> getBlockTag(JsonObject jsonObject, String fieldName) {
        String tagName = JSONUtils.getString(jsonObject, fieldName);
        ITag<Block> blockITag = TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation(tagName));
        if (blockITag != null) {
            return blockITag;
        } else {
            throw new JsonParseException("Failed to find Block Tag for " + tagName);
        }
    }

    public static ITag<Block> getBlockTag(JsonObject jsonObject) {
        return getBlockTag(jsonObject, "tag");
    }
}
