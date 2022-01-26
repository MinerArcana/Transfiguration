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
        if (object instanceof BlockState) {
            Level world = container.getLevel();
            BlockPos blockPos = container.getTargetedPos();
            BlockState blockState = (BlockState) object;
            if (blockState.getPistonPushReaction() != PushReaction.BLOCK && world.getBlockEntity(blockPos) == null &&
                    blockState.getDestroySpeed(world, blockPos) > 0) {
                world.addFreshEntity(new FallingBlockEntity(
                        world,
                        blockPos.getX() + 0.5,
                        blockPos.getY(),
                        blockPos.getZ() + 0.5,
                        ((BlockState) object)
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
