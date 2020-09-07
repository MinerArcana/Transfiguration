package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class BlockIngredientSerializer<T extends BlockIngredient>
        extends ForgeRegistryEntry<BlockIngredientSerializer<?>> implements ISerializer<T> {

}
