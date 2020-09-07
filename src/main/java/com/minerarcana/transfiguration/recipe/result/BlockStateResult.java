package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class BlockStateResult extends Result {
    private final BlockState blockState;

    private BlockStateResult(BlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    public ActionResultType handle(TransfigurationContainer transfigurationContainer) {
        transfigurationContainer.getWorld().setBlockState(transfigurationContainer.getTargetedPos(), blockState);
        return ActionResultType.SUCCESS;
    }

    @Override
    public ItemStack getOutputRepresentation() {
        return new ItemStack(blockState.getBlock().asItem());
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return null;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public static BlockStateResult create(BlockState blockState) {
        return new BlockStateResult(blockState);
    }

    public static BlockStateResult create(Block block) {
        return new BlockStateResult(block.getDefaultState());
    }
}
