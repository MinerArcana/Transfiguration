package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TagJson {
    public static ITag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject, String fieldName) {
        String tagName = JSONUtils.getString(jsonObject, fieldName);
        return TagCollectionManager.func_232928_e_().func_232927_d_().get(new ResourceLocation(tagName));
    }

    public static ITag<EntityType<?>> getEntityTypeTag(JsonObject jsonObject) {
        return getEntityTypeTag(jsonObject, "tag");
    }
}
