package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import javax.annotation.Nonnull;

public class MatchIngredient extends BasicIngredient {
    private final EntityType<?> entityType;
    private final Block block;

    public MatchIngredient(EntityType<?> entityType) {
        this.entityType = entityType;
        this.block = null;
    }

    public MatchIngredient(Block block) {
        this.entityType = null;
        this.block = block;
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entityType != null && entity.getType() == entityType;
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        return block != null && blockState.getBlock() == block;
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get();
    }

    public Block getBlock() {
        return block;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }
}
