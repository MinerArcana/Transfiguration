package com.minerarcana.transfiguration.recipe.ingedient.entity;

import com.minerarcana.transfiguration.recipe.ingedient.serializer.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class EntityIngredientSerializer<T extends EntityIngredient>
        extends ForgeRegistryEntry<EntityIngredientSerializer<?>> implements ISerializer<T> {

}
