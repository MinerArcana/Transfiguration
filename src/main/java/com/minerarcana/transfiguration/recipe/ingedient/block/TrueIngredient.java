package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.BlockState;

import javax.annotation.Nonnull;

public class TrueIngredient extends BlockIngredient {
    @Nonnull
    @Override
    public BlockIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.TRUE_BLOCK_INGREDIENT_SERIALIZER.get();
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        return true;
    }
}
