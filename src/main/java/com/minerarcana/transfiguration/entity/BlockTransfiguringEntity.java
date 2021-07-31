package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockTransfiguringEntity extends TransfiguringEntity<BlockTransfigurationRecipe, BlockIngredient, BlockState> {
    public BlockTransfiguringEntity(EntityType<? extends TransfiguringEntity> entityType, World world) {
        super(entityType, world);
    }

    public BlockTransfiguringEntity(World world, BlockPos blockPos, BlockTransfigurationRecipe recipe, int modifiedTime, double powerModifier) {
        super(TransfigurationEntities.BLOCK_TRANSFIGURING.get(), world, blockPos, recipe, modifiedTime, powerModifier);
    }

    @Nonnull
    @Override
    public TransfigurationContainer<BlockState> createTransfigurationContainer() {
        return TransfigurationContainer.block(
                world,
                this.getPosition(),
                this.getCaster()
        );
    }

    @Nullable
    @Override
    public BlockTransfigurationRecipe getRecipe() {
        return this.getEntityWorld()
                .getRecipeManager()
                .getRecipe(new ResourceLocation(this.getRecipeName()))
                .filter(BlockTransfigurationRecipe.class::isInstance)
                .map(BlockTransfigurationRecipe.class::cast)
                .orElse(null);
    }
}
