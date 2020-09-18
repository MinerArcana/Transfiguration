package com.minerarcana.transfiguration.recipe.entity;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class EntityTransfigurationRecipe implements IRecipe<TransfigurationContainer<Entity>> {
    private final ResourceLocation recipeId;
    private final TransfigurationType transfigurationType;
    private final EntityIngredient ingredient;
    private final Result result;

    public EntityTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                       EntityIngredient ingredient, Result result) {
        this.transfigurationType = transfigurationType;
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(TransfigurationContainer<Entity> inv, World world) {
        return ingredient.test(inv.getTargeted());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getCraftingResult(TransfigurationContainer<Entity> inv) {
        return result.getOutputRepresentation();
    }

    public ActionResultType transfigure(TransfigurationContainer<Entity> inv) {
        inv.getTargeted().remove(false);
        return result.handle(inv);
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
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
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return transfigurationType.getEntityRecipeType();
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public EntityIngredient getIngredient() {
        return ingredient;
    }

    public Result getResult() {
        return result;
    }
}
