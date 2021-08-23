package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
            World world = container.getWorld();
            BlockPos blockPos = container.getTargetedPos();
            BlockState blockState = (BlockState) object;
            if (blockState.getPushReaction() != PushReaction.BLOCK && world.getTileEntity(blockPos) == null) {
                world.addEntity(new FallingBlockEntity(
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
