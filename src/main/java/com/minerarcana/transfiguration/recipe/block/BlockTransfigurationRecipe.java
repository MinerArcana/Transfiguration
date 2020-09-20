package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.resulthandler.DoOnceResultHandler;
import com.minerarcana.transfiguration.recipe.resulthandler.ResultHandler;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.block.BlockState;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BlockTransfigurationRecipe extends TransfigurationRecipe<BlockIngredient, BlockState> {
    public BlockTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                      BlockIngredient ingredient, Result result, int ticks) {
        super(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    public ActionResultType transfigure(TransfigurationContainer<BlockState> transfigurationContainer, double powerModifier) {
        BlockTransfiguringEntity transfiguringEntity = new BlockTransfiguringEntity(transfigurationContainer.getWorld(),
                transfigurationContainer.getOnSide().getOpposite(), this, this.getTicks(), powerModifier);
        transfiguringEntity.setPosition(transfigurationContainer.getTargetedPos().offset(
                transfigurationContainer.getOnSide()));
        transfigurationContainer.getWorld().addEntity(transfiguringEntity);
        return ActionResultType.SUCCESS;
    }

    @Override
    public ResultHandler createResultHandler() {
        return new DoOnceResultHandler((container, powerModifier) -> this.getResult().handle(container));
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return this.getTransfigurationType().getBlockRecipeType();
    }

}
