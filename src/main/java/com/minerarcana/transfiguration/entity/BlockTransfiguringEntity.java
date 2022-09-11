package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockTransfiguringEntity extends TransfiguringEntity<BlockTransfigurationRecipe, BlockState> {
    public BlockTransfiguringEntity(EntityType<? extends TransfiguringEntity> entityType, Level world) {
        super(entityType, world);
    }

    public BlockTransfiguringEntity(Level world, BlockPos blockPos, BlockTransfigurationRecipe recipe, double timeModifier, double powerModifier) {
        super(TransfigurationEntities.BLOCK_TRANSFIGURING.get(), world, blockPos, recipe, timeModifier, powerModifier);
    }

    @Override
    protected boolean spread(BlockTransfigurationRecipe currentRecipe, TransfigurationContainer<BlockState> container) {
        //From a shuffled list of all six directions, attempt to spread to the first 2^(space + 0.5) rounded
        //Only successfully spread if no other BlockTransfiguringEntity in new pos, and new pos has the same block as this entity's currently targeted block
        //Half space(power modifier) each spread to avoid runaway

        //int numSpread = Math.min(2 + (int)Math.floor(this.getPowerModifier()), 6);
        int numSpread = Math.min((int) Math.round(Math.pow(2, this.getPowerModifier() + 0.5)), 6);
        List<Direction> spreadDirectionsList = Arrays.asList(Direction.values());
        Collections.shuffle(spreadDirectionsList);

        for (Direction d : spreadDirectionsList.subList(0, numSpread)) {
            BlockPos pos = this.blockPosition().offset(d.getNormal());
            TransfigurationContainer<BlockState> newTransContainer = this.createTransfigurationContainer(pos);
            if (container.getTargeted().getBlock().equals(newTransContainer.getTargeted().getBlock())
                    && this.getCommandSenderWorld().getEntitiesOfClass(BlockTransfiguringEntity.class, new AABB(pos), entity -> entity != this).isEmpty()) {
                BlockTransfigurationRecipe.tryTransfigure(
                        currentRecipe.getTransfigurationType(),
                        newTransContainer,
                        this.getPowerModifier() / (this.isSkipping() ? 1D : 2.0D),
                        this.getTimeModifier()
                );
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public TransfigurationContainer<BlockState> createTransfigurationContainer() {
        return TransfigurationContainer.block(
                level,
                this.blockPosition(),
                this.getCaster()
        );
    }

    @Nonnull
    public TransfigurationContainer<BlockState> createTransfigurationContainer(BlockPos pos) {
        return TransfigurationContainer.block(
                level,
                pos,
                this.getCaster()
        );
    }

    @Nullable
    @Override
    public BlockTransfigurationRecipe getRecipe() {
        return this.getCommandSenderWorld()
                .getRecipeManager()
                .byKey(new ResourceLocation(this.getRecipeName()))
                .filter(BlockTransfigurationRecipe.class::isInstance)
                .map(BlockTransfigurationRecipe.class::cast)
                .orElse(null);
    }

    @Override
    public void removeInput() {
        this.getCommandSenderWorld().setBlockAndUpdate(this.blockPosition(), Blocks.AIR.defaultBlockState());
    }
}
