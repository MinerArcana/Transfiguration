package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.util.NonNullPredicate;

public abstract class EntityIngredient implements NonNullPredicate<Entity>,
        ISerializable<EntityIngredientSerializer<? extends EntityIngredient>> {
}
