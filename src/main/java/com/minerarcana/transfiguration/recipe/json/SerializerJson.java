package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.RecipeResult;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.serializer.ISerializable;
import com.minerarcana.transfiguration.recipe.ingedient.serializer.ISerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

public class SerializerJson {

    @Nonnull
    public static BlockIngredient getBlockIngredient(JsonObject jsonObject) {
        return getBlockIngredient(jsonObject, "ingredient");
    }

    @Nonnull
    public static BlockIngredient getBlockIngredient(JsonObject jsonObject, String fieldName) {
        return getSerializable(jsonObject, fieldName, Transfiguration.blockIngredientSerializers::getValue);
    }

    @Nonnull
    public static RecipeResult getRecipeResult(JsonObject jsonObject) {
        return getRecipeResult(jsonObject, "result");
    }

    @Nonnull
    public static RecipeResult getRecipeResult(JsonObject jsonObject, String fieldName) {
        return getSerializable(jsonObject, fieldName, resourceLocation -> null);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static <T extends ISerializer<? extends U>, U extends ISerializable<?>> U getSerializable(
            JsonObject jsonObject, String fieldName, Function<ResourceLocation, T> registry) {
        JsonObject ingredientObject = jsonObject.getAsJsonObject(fieldName);
        if (ingredientObject == null) {
            throw new JsonParseException("Failed to Find JsonObject for Field '" + fieldName + "'");
        } else {
            JsonPrimitive type = ingredientObject.getAsJsonPrimitive("type");
            if (type == null) {
                throw new JsonParseException("Failed to Find String for Field 'type'");
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
