package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.BlockState;

import javax.annotation.Nonnull;

public class NotBlockIngredient extends BlockIngredient {
    private final BlockIngredient[] blockIngredients;

    public NotBlockIngredient(BlockIngredient... blockIngredient) {
        this.blockIngredients = blockIngredient;
    }

    @Nonnull
    @Override
    public BlockIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.NOT_BLOCK_INGREDIENT_SERIALIZER.get();
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        for (BlockIngredient blockIngredient : blockIngredients) {
            if (blockIngredient.test(blockState)) {
                return false;
            }
        }
        return true;
    }

    public BlockIngredient[] getBlockIngredients() {
        return blockIngredients;
    }
}
