package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class ObjectJson {
    private static final Gson GSON = new Gson();

    public static JsonObject writeItemStack(ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString());
        if (itemStack.getCount() > 1) {
            jsonObject.addProperty("count", itemStack.getCount());
        }
        if (itemStack.getTag() != null) {
            jsonObject.add("nbt", GSON.toJsonTree(itemStack.getTag().toString()));
        }
        return jsonObject;
    }
}
