package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class BasicIngredientSerializer<T extends BasicIngredient> extends ForgeRegistryEntry<BasicIngredientSerializer<?>>
        implements ISerializer<T> {
}
