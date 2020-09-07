package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ResultSerializer<T extends Result> extends ForgeRegistryEntry<ResultSerializer<?>>
 implements ISerializer<T> {
}
