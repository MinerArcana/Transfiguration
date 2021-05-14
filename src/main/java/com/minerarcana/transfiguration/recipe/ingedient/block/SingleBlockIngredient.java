package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

public class SingleBlockIngredient extends BlockIngredient {
    private final Block block;

    private SingleBlockIngredient(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(BlockState blockState) {
        return blockState.getBlock() == block;
    }

    public Block getBlock() {
        return block;
    }

    @Nonnull
    @Override
    public BlockIngredientSerializer<? extends BlockIngredient> getSerializer() {
        return TransfigurationRecipes.SINGLE_BLOCK_INGREDIENT_SERIALIZER.get();
    }

    @Override
    public List<BlockState> getMatching() {
        return block.getStateContainer().getValidStates();
    }

    public static SingleBlockIngredient create(@Nonnull Block block) {
        return new SingleBlockIngredient(block);
    }
}
