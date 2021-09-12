package com.minerarcana.transfiguration.recipe.json;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Function;

public class SerializerJson {

    @Nonnull
    public static BasicIngredient getBasicIngredient(JsonObject jsonObject) {
        return getBasicIngredient(jsonObject, "ingredient");
    }

    @Nonnull
    public static BasicIngredient getBasicIngredient(JsonObject jsonObject, String fieldName) {
        return getSerializable(
                jsonObject,
                fieldName,
                Transfiguration.basicIngredientSerializers::getValue,
                TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get()::parse
        );
    }

    @Nonnull
    public static List<BasicIngredient> getBasicIngredients(JsonObject jsonObject) {
        JsonArray ingredientsJson = JSONUtils.getJsonArray(jsonObject, "ingredient");
        List<BasicIngredient> ingredients = Lists.newArrayList();
        for (JsonElement jsonElement : ingredientsJson) {
            if (jsonElement.isJsonObject()) {
                ingredients.add(getSerializable(
                        jsonElement.getAsJsonObject(),
                        Transfiguration.basicIngredientSerializers::getValue,
                        TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get()::parse
                ));
            } else {
                throw new JsonParseException("Expected Object in 'ingredient' array");
            }
        }
        return ingredients;
    }

    @Nonnull
    public static Result getResult(JsonObject jsonObject) {
        return getResult(jsonObject, "result");
    }

    @Nonnull
    public static Result getResult(JsonObject jsonObject, String fieldName) {
        return getSerializable(jsonObject, fieldName, Transfiguration.resultSerializers::getValue,
                TransfigurationRecipes.BLOCK_STATE_RESULT_SERIALIZER.get()::parse);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static <T extends ISerializer<? extends U>, U extends ISerializable<?>> U getSerializable(
            JsonObject jsonObject, String fieldName, Function<ResourceLocation, T> registry,
            @Nullable Function<JsonObject, U> defaultSerializer) {
        JsonObject ingredientObject = jsonObject.getAsJsonObject(fieldName);
        if (ingredientObject == null) {
            throw new JsonParseException("Failed to Find JsonObject for Field '" + fieldName + "'");
        } else {
            return getSerializable(ingredientObject, registry, defaultSerializer);
        }
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static <T extends ISerializer<? extends U>, U extends ISerializable<?>> U getSerializable(
            JsonObject ingredientObject,
            Function<ResourceLocation, T> registry,
            @Nullable Function<JsonObject, U> defaultSerializer
    ) {
        JsonPrimitive type = ingredientObject.getAsJsonPrimitive("type");
        if (type == null) {
            if (defaultSerializer != null) {
                return defaultSerializer.apply(ingredientObject);
            } else {
                throw new JsonParseException("Failed to Find String for Field 'type'");
            }
        } else {
            T ingredientSerializer = registry.apply(new ResourceLocation(type.getAsString()));
            if (ingredientSerializer == null) {
                throw new JsonParseException("Failed to Find Serializer for type '" + type.getAsString() + "'");
            } else {
                return ingredientSerializer.parse(ingredientObject);
            }
        }
    }
}
