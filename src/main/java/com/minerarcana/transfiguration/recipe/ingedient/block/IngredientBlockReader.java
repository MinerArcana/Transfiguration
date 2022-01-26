package com.minerarcana.transfiguration.recipe.ingedient.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IngredientBlockReader implements BlockGetter {
    private final BlockState blockState;

    public IngredientBlockReader(BlockState blockState) {
        this.blockState = blockState;
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(@Nonnull BlockPos pos) {
        return null;
    }

    @Override
    @Nonnull
    public BlockState getBlockState(@Nonnull BlockPos pos) {
        return pos.equals(BlockPos.ZERO) ? blockState : Blocks.AIR.defaultBlockState();
    }

    @Override
    @Nonnull
    public FluidState getFluidState(@Nonnull BlockPos pos) {
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getMinBuildHeight() {
        return 0;
    }
}
