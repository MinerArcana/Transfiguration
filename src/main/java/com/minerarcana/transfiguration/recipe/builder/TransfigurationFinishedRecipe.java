package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TransfigurationFinishedRecipe<T extends ISerializer<?> & IForgeRegistryEntry<T>> implements FinishedRecipe {

    private final RecipeSerializer<?> recipeSerializer;
    private final ResourceLocation id;
    private final TransfigurationType transfigurationType;
    private final IFinishedObject<T> ingredient;
    private final IFinishedObject<ResultSerializer<?>> result;
    private final int ticks;

    public TransfigurationFinishedRecipe(RecipeSerializer<?> recipeSerializer, ResourceLocation id,
                                         TransfigurationType transfigurationType, IFinishedObject<T> ingredient,
                                         IFinishedObject<ResultSerializer<?>> result, int ticks) {
        this.recipeSerializer = recipeSerializer;
        this.id = id;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.ticks = ticks;
    }

    @Override
    public void serializeRecipeData(@Nonnull JsonObject json) {
        json.addProperty("transfigurationType", Objects.requireNonNull(transfigurationType.getRegistryName()).toString());
        json.add("ingredient", ingredient.getJson());
        json.add("result", result.getJson());
        json.addProperty("ticks", ticks);
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getType() {
        return recipeSerializer;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
