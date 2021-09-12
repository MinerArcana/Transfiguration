package com.minerarcana.transfiguration.recipe;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public abstract class TransfigurationRecipe<U> implements ITransfigurationRecipe<U> {
    private final ResourceLocation recipeId;
    private final TransfigurationType transfigurationType;
    private final BasicIngredient ingredient;
    private final Result result;
    private final int ticks;

    public TransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType, BasicIngredient ingredient,
                                 Result result, int ticks) {
        this.recipeId = recipeId;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.ticks = ticks;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull TransfigurationContainer<U> transfigurationContainer) {
        return result.getRepresentation().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return result.getRepresentation();
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public boolean transfigure(TransfigurationContainer<U> transfigurationContainer, double powerModifier, double timeModifier) {
        return transfigurationContainer.getWorld().isRemote() || transfigurationContainer.getWorld().addEntity(
                this.createTransfiguringEntity(transfigurationContainer, timeModifier, powerModifier)
        );
    }

    public abstract TransfiguringEntity<?, U> createTransfiguringEntity(
            TransfigurationContainer<U> transfigurationContainer, double timeModifier, double powerModifier
    );

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public BasicIngredient getIngredient() {
        return ingredient;
    }

    public Result getResult() {
        return result;
    }

    public int getTicks() {
        return ticks;
    }
}
