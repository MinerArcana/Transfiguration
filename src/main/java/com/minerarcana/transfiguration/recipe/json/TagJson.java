package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;

public class TagJson {
    public static ITag.INamedTag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject, String fieldName) {
        String tagName = JSONUtils.getString(jsonObject, fieldName);
        return EntityTypeTags.func_232896_a_(tagName);
    }

    public static ITag.INamedTag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject) {
        return getEntityTypeTag(jsonObject, "tag");
    }
}
