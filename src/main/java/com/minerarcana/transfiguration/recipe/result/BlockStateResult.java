package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class BlockStateResult extends Result {
    private final BlockState blockState;

    private BlockStateResult(BlockState blockState) {
        this.blockState = blockState;
    }


    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(this::handle);
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        transfigurationContainer.getLevel().setBlockAndUpdate(transfigurationContainer.getTargetedPos(), blockState);
    }

    @Override
    @Nonnull
    public ItemStack getRepresentation() {
        return new ItemStack(blockState.getBlock().asItem());
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_STATE_RESULT_SERIALIZER.get();
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public static BlockStateResult create(BlockState blockState) {
        return new BlockStateResult(blockState);
    }

    public static BlockStateResult create(Block block) {
        return new BlockStateResult(block.defaultBlockState());
    }
}
