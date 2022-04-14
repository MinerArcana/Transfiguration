package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TagIngredient extends BasicIngredient {
    private final TagKey<Block> blockTag;
    private final TagKey<EntityType<?>> entityTag;

    public TagIngredient(TagKey<Block> blockTag, TagKey<EntityType<?>> entityTag) {
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

    public TagKey<Block> getBlockTag() {
        return blockTag;
    }

    public TagKey<EntityType<?>> getEntityTag() {
        return entityTag;
    }
}
