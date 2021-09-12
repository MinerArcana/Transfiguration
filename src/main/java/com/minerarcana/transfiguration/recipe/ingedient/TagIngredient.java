package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;

import javax.annotation.Nonnull;

public class TagIngredient extends BasicIngredient {
    private final ITag<Block> blockTag;
    private final ITag<EntityType<?>> entityTag;

    public TagIngredient(ITag<Block> blockTag, ITag<EntityType<?>> entityTag) {
        this.blockTag = blockTag;
        this.entityTag = entityTag;
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entityTag != null && entity.getType().isContained(entityTag);
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        return blockTag != null && blockState.isIn(blockTag);
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get();
    }

    public ITag<Block> getBlockTag() {
        return blockTag;
    }

    public ITag<EntityType<?>> getEntityTag() {
        return entityTag;
    }
}
