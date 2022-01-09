package com.minerarcana.transfiguration.recipe.ingedient.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IngredientBlockReader implements IBlockReader {
    private final BlockState blockState;

    public IngredientBlockReader(BlockState blockState) {
        this.blockState = blockState;
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(@Nonnull BlockPos pos) {
        return null;
    }

    @Override
    @Nonnull
    public BlockState getBlockState(@Nonnull BlockPos pos) {
        return pos.equals(BlockPos.ZERO) ? blockState : Blocks.AIR.getDefaultState();
    }

    @Override
    @Nonnull
    public FluidState getFluidState(@Nonnull BlockPos pos) {
        return Fluids.EMPTY.getDefaultState();
    }
}
