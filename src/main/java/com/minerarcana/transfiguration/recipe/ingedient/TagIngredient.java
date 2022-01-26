package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TagIngredient extends BasicIngredient {
    private final Tag<Block> blockTag;
    private final Tag<EntityType<?>> entityTag;

    public TagIngredient(Tag<Block> blockTag, Tag<EntityType<?>> entityTag) {
        this.blockTag = blockTag;
        this.entityTag = entityTag;
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entityTag != null && entity.getType().is(entityTag);
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        return blockTag != null && blockState.is(blockTag);
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get();
    }

    public Tag<Block> getBlockTag() {
        return blockTag;
    }

    public Tag<EntityType<?>> getEntityTag() {
        return entityTag;
    }
}
