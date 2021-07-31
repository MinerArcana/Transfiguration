package com.minerarcana.transfiguration.recipe.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.resulthandler.DoOnceResultHandler;
import com.minerarcana.transfiguration.recipe.resulthandler.ResultHandler;
import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.entity.Entity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class EntityTransfigurationRecipe extends TransfigurationRecipe<EntityIngredient, Entity> {
    public EntityTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                       EntityIngredient ingredient, Result result, int time) {
        super(recipeId, transfigurationType, ingredient, result, time);
    }

    @Override
    public ActionResultType transfigure(TransfigurationContainer<Entity> transfigurationContainer, double powerModifier) {

        return ActionResultType.SUCCESS;
    }

    @Override
    public ResultHandler createResultHandler() {
        return new DoOnceResultHandler((transfigurationContainer, power) -> {
            this.getResult().handle(transfigurationContainer);
        });
    }


    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return this.getTransfigurationType().getEntityRecipeType();
    }

}
