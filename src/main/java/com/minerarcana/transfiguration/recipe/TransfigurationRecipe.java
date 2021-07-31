package com.minerarcana.transfiguration.recipe;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.resulthandler.ResultHandler;
import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullPredicate;

import javax.annotation.Nonnull;

public abstract class TransfigurationRecipe<T extends NonNullPredicate<U>, U> implements IRecipe<TransfigurationContainer<U>> {
    private final ResourceLocation recipeId;
    private final TransfigurationType transfigurationType;
    private final T ingredient;
    private final Result result;
    private final int ticks;

    public TransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType, T ingredient,
                                 Result result, int ticks) {
        this.recipeId = recipeId;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.ticks = ticks;
    }

    @Override
    public boolean matches(TransfigurationContainer<U> transfigurationContainer, @Nonnull World world) {
        return ingredient.test(transfigurationContainer.getTargeted());
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull TransfigurationContainer<U> transfigurationContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return result.getOutputRepresentation();
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

    public abstract ActionResultType transfigure(TransfigurationContainer<U> transfigurationContainer, double powerModifier);

    public abstract ResultHandler createResultHandler();

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public T getIngredient() {
        return ingredient;
    }

    public Result getResult() {
        return result;
    }

    public int getTicks() {
        return ticks;
    }
}
