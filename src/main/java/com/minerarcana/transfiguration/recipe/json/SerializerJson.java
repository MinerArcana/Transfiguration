package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

public class SerializerJson {

    @Nonnull
    public static BlockIngredient getBlockIngredient(JsonObject jsonObject) {
        return getBlockIngredient(jsonObject, "ingredient");
    }

    @Nonnull
    public static BlockIngredient getBlockIngredient(JsonObject jsonObject, String fieldName) {
        return getSerializable(jsonObject, fieldName, Transfiguration.blockIngredientSerializers::getValue,
                TransfigurationRecipes.SINGLE_BLOCK_INGREDIENT_SERIALIZER.get()::parse);
    }

    @Nonnull
    public static EntityIngredient getEntityIngredient(JsonObject jsonObject) {
        return getEntityIngredient(jsonObject, "ingredient");
    }

    @Nonnull
    public static EntityIngredient getEntityIngredient(JsonObject jsonObject, String fieldName) {
        return getSerializable(jsonObject, fieldName, Transfiguration.entityIngredientSerializers::getValue,
                TransfigurationRecipes.ENTITY_TYPE_ENTITY_INGREDIENT_SERIALIZER.get()::parse);
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
}
