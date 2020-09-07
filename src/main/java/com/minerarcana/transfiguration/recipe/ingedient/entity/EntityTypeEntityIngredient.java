package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.serializer.ISerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import javax.annotation.Nonnull;

public class EntityTypeEntityIngredient extends EntityIngredient {
    private final EntityType<?> entityType;

    private EntityTypeEntityIngredient(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @Nonnull
    @Override
    public EntityIngredientSerializer<? extends EntityIngredient> getSerializer() {
        return TransfigurationRecipes.ENTITY_TYPE_ENTITY_INGREDIENT_SERIALIZER.get();
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entity.getType() == entityType;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public static EntityTypeEntityIngredient create(@Nonnull EntityType<?> entityType) {
        return new EntityTypeEntityIngredient(entityType);
    }
}
