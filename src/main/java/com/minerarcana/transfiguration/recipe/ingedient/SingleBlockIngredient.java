package com.minerarcana.transfiguration.recipe.ingedient;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class SingleBlockIngredient extends BlockIngredient {
    private final Block block;

    public SingleBlockIngredient(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(BlockState blockState) {
        return blockState.getBlock() == block;
    }
}
