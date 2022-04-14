package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;

public class FallingBlockResult extends Result {
    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(0, false, this::handle);
    }

    private void handle(TransfigurationContainer<?> container, double powerModifier) {
        Object object = container.getTargeted();
        if (object instanceof BlockState blockState) {
            Level level = container.getLevel();
            BlockPos blockPos = container.getTargetedPos();
            if (blockState.getPistonPushReaction() != PushReaction.BLOCK && level.getBlockEntity(blockPos) == null &&
                    blockState.getDestroySpeed(level, blockPos) > 0) {
                level.addFreshEntity(FallingBlockEntity.fall(
                        level,
                        blockPos,
                        blockState
                ));
            }
        }
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.FALLING_RESULT_SERIALIZER.get();
    }
}
