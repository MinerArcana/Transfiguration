package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

public class BlockTransfigurationRecipe extends TransfigurationRecipe<BlockIngredient, BlockState> {
    public BlockTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                      BlockIngredient ingredient, Result result, int ticks) {
        super(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    public boolean transfigure(TransfigurationContainer<BlockState> transfigurationContainer, double powerModifier) {
        BlockTransfiguringEntity transfiguringEntity = new BlockTransfiguringEntity(transfigurationContainer.getWorld(),
                transfigurationContainer.getTargetedPos(), this, this.getTicks(), powerModifier);
        transfigurationContainer.getWorld().addEntity(transfiguringEntity);
        return true;
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

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryTransfigure(TransfigurationType type, BlockRayTraceResult blockRayTraceResult, World world,
                                         @Nullable Entity entity) {
        return tryTransfigure(type, TransfigurationContainer.block(
                world,
                blockRayTraceResult.getPos(),
                entity
        ));
    }

    public static boolean tryTransfigure(TransfigurationType type, TransfigurationContainer<BlockState> container) {
        if (type != null) {
            World world = container.getWorld();
            Optional<ITransfigurationRecipe<BlockState>> recipeOptional = world.getRecipeManager()
                    .getRecipe(type.getBlockRecipeType(), container, world);
            if (!recipeOptional.isPresent()) {
                Iterator<Supplier<TransfigurationType>> supplierIterator = type.getIncludedTypes().iterator();
                while (!recipeOptional.isPresent() && supplierIterator.hasNext()) {
                    recipeOptional = world.getRecipeManager()
                            .getRecipe(supplierIterator.next().get().getBlockRecipeType(), container, world);
                }
            }
            return recipeOptional.map(recipe -> recipe.transfigure(container, 1.0F))
                    .orElse(false);
        }

        return false;
    }
}
