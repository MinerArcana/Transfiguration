package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;

import javax.annotation.Nonnull;

public class TagEntityIngredient extends EntityIngredient {
    private final ITag<EntityType<?>> tag;

    public TagEntityIngredient(ITag<EntityType<?>> tag) {
        this.tag = tag;
    }

    @Nonnull
    @Override
    public EntityIngredientSerializer<? extends EntityIngredient> getSerializer() {
        return TransfigurationRecipes.TAG_ENTITY_INGREDIENT_SERIALIZER.get();
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entity.getType().isContained(tag);
    }

    public ITag<EntityType<?>> getTag() {
        return tag;
    }
}
