package com.minerarcana.transfiguration.recipe.json;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ObjectJson {
    private static final Gson GSON = new Gson();

    public static JsonObject writeItemStack(ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStack.getItem())).toString());
        if (itemStack.getCount() > 1) {
            jsonObject.addProperty("count", itemStack.getCount());
        }
        if (itemStack.getTag() != null) {
            jsonObject.add("nbt", GSON.toJsonTree(itemStack.getTag().toString()));
        }
        return jsonObject;
    }

    public static <T> JsonArray writeArray(Function<T, JsonElement> convert, T[] list) {
        JsonArray jsonElements = new JsonArray();
        for (T object : list) {
            jsonElements.add(convert.apply(object));
        }
        return jsonElements;
    }

    public static <E extends Enum<E>> List<E> getEnumFromJson(JsonObject jsonObject, String field, Class<E> enumClass) {
        List<E> enumList = Lists.newArrayList();
        JsonElement enumElement = jsonObject.get(field);
        if (enumElement != null) {
            if (enumElement.isJsonPrimitive()) {
                String enumName = enumElement.getAsString();
                for (E enumValue : enumClass.getEnumConstants()) {
                    if (enumValue.name().equalsIgnoreCase(enumName)) {
                        enumList.add(enumValue);
                    }
                }
            } else if (enumElement.isJsonArray()) {
                for (JsonElement jsonElement : enumElement.getAsJsonArray()) {
                    String enumName = jsonElement.getAsString();
                    for (E enumValue : enumClass.getEnumConstants()) {
                        if (enumValue.name().equalsIgnoreCase(enumName)) {
                            enumList.add(enumValue);
                        }
                    }
                }
            }
        }

        return enumList;
    }

    public static <T extends Number & Comparable<T>> Range<T> getRange(JsonObject jsonObject, String fieldName,
                                                                       Function<JsonElement, T> transformer) {
        Range<T> range;
        if (jsonObject.has(fieldName) && jsonObject.get(fieldName).isJsonObject()) {
            JsonObject rangeObject = jsonObject.getAsJsonObject(fieldName);
            range = Range.between(
                    transformer.apply(rangeObject.has("min") ? rangeObject.get("min") : new JsonPrimitive(0)),
                    transformer.apply(rangeObject.has("max") ? rangeObject.get("max") : new JsonPrimitive(Integer.MAX_VALUE))
            );
        } else {
            range = Range.between(
                    transformer.apply(new JsonPrimitive(0)),
                    transformer.apply(new JsonPrimitive(Integer.MAX_VALUE))
            );
        }
        return range;
    }

    public static Pair<Ingredient, Ingredient> getViewOverrides(JsonObject jsonObject) {
        Ingredient ingredient = null;
        Ingredient result = null;
        if (jsonObject.has("view")) {
            JsonObject viewObject = GsonHelper.getAsJsonObject(jsonObject, "view");
            if (viewObject.has("ingredient")) {
                ingredient = CraftingHelper.getIngredient(viewObject.get("ingredient"));
                if (ingredient.isEmpty()) {
                    throw new JsonParseException("Failed to find valid Ingredient in view.ingredient");
                }
            }
            if (viewObject.has("result")) {
                result = CraftingHelper.getIngredient(viewObject.get("result"));
                if (result.isEmpty()) {
                    throw new JsonParseException("Failed to find valid Ingredient in view.result");
                }
            }
        }
        return Pair.of(ingredient, result);
    }
}
