package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TransfigurationFinishedRecipe<T extends ISerializer<?> & IForgeRegistryEntry<T>> implements IFinishedRecipe {

    private final IRecipeSerializer<?> recipeSerializer;
    private final ResourceLocation id;
    private final TransfigurationType transfigurationType;
    private final IFinishedObject<T> ingredient;
    private final IFinishedObject<ResultSerializer<?>> result;
    private final Advancement.Builder advancementBuilder;
    private final ResourceLocation advancementID;

    public TransfigurationFinishedRecipe(IRecipeSerializer<?> recipeSerializer, String recipeType, ResourceLocation id,
                                         TransfigurationType transfigurationType, IFinishedObject<T> ingredient,
                                         IFinishedObject<ResultSerializer<?>> result, @Nullable Advancement.Builder advancementBuilder) {
        this.recipeSerializer = recipeSerializer;
        this.id = id;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.advancementBuilder = advancementBuilder;
        if (advancementBuilder != null) {
            this.advancementID = new ResourceLocation(id.getNamespace(), "recipes/transfiguration/" + recipeType +
                    "/" + id.getPath());
        } else {
            this.advancementID = null;
        }
    }

    @Override
    public void serialize(@Nonnull JsonObject json) {
        json.addProperty("transfigurationType", Objects.requireNonNull(transfigurationType.getRegistryName()).toString());
        json.add("ingredient", ingredient.getJson());
        json.add("result", result.getJson());
    }

    @Override
    @Nonnull
    public ResourceLocation getID() {
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return recipeSerializer;
    }

    @Nullable
    @Override
    public JsonObject getAdvancementJson() {
        return advancementBuilder != null ? advancementBuilder.serialize() : null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementID() {
        return advancementID;
    }
}
