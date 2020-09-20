package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockTransfiguringEntity extends TransfiguringEntity<BlockTransfigurationRecipe, BlockIngredient, BlockState> {
    public BlockTransfiguringEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public BlockTransfiguringEntity(World world, Direction placedOn, BlockTransfigurationRecipe recipe, int modifiedTime, double powerModifier) {
        super(TransfigurationEntities.BLOCK_TRANSFIGURING.get(), world, placedOn, recipe, modifiedTime, powerModifier);
    }

    @Nonnull
    @Override
    public TransfigurationContainer<BlockState> createTransfigurationContainer() {
        return TransfigurationContainer.block(world, this.getPosition().offset(Direction.DOWN), this.getPlacedOn(),
                this.getCaster());
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
