package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TransfigurationRecipeBuilder {
    private final TransfigurationType transfigurationType;
    private final IRecipeSerializer<?> recipeSerializer;
    private IFinishedObject<BasicIngredientSerializer<?>> ingredient;
    private IFinishedObject<ResultSerializer<?>> result;
    private int ticks = 12 * 20;

    private TransfigurationRecipeBuilder(TransfigurationType transfigurationType, IRecipeSerializer<?> recipeSerializer) {
        this.transfigurationType = transfigurationType;
        this.recipeSerializer = recipeSerializer;
    }

    public static TransfigurationRecipeBuilder create(TransfigurationType transfigurationType) {
        return new TransfigurationRecipeBuilder(transfigurationType, TransfigurationRecipes.BLOCK_TRANSFIGURATION.get());
    }

    public static TransfigurationRecipeBuilder create(Supplier<? extends TransfigurationType> transfigurationType) {
        return create(transfigurationType.get());
    }

    public TransfigurationRecipeBuilder withIngredient(IFinishedObject<BasicIngredientSerializer<?>> ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public TransfigurationRecipeBuilder withResult(IFinishedObject<ResultSerializer<?>> result) {
        this.result = result;
        return this;
    }

    public TransfigurationRecipeBuilder withTicks(int ticks) {
        this.ticks = ticks;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer) {
        this.build(recipeConsumer, null);
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer, @Nullable ResourceLocation id) {
        this.validate(id);
        if (id == null) {
            ResourceLocation resultId = this.result.getId();
            id = new ResourceLocation(resultId.getNamespace(), "transfiguration/" + resultId.getPath().replace("/", "_")
                    + "_from_" + Objects.requireNonNull(transfigurationType.getRegistryName()).getPath().replace("/", "_")
                    + "_" + ingredient.getId().toString().replace(":", "_").replace("/", "_"));
        }
        recipeConsumer.accept(new TransfigurationFinishedRecipe<>(recipeSerializer, id, transfigurationType,
                ingredient, result, ticks));
    }

    protected void validate(ResourceLocation id) {
        if (result == null) {
            throw new IllegalStateException("No 'result' defined for Transfiguration Recipe " + id + "!");
        }

        if (ingredient == null) {
            throw new IllegalStateException("No 'ingredient' defined for Transfiguration Recipe " + id + "!");
        }

        if (transfigurationType == null) {
            throw new IllegalStateException("No 'transfigurationType' defined for Transfiguration Recipe " + id + "!");
        }
    }
}
