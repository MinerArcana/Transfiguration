package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class Result implements ISerializable<ResultSerializer<?>> {
    @Nonnull
    public abstract ResultInstance create();

    @Nonnull
    public abstract ItemStack getRepresentation();
}
