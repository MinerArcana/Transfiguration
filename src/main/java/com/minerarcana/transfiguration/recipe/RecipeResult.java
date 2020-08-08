package com.minerarcana.transfiguration.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.NonNullLazy;

public class RecipeResult {
    private final BlockState blockState;

    public RecipeResult(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public ItemStack getRecipeOutput() {
        return new ItemStack(blockState.getBlock().asItem());
    }
}
