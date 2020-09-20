package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public abstract class Result implements ISerializable<ResultSerializer<?>> {

    @Nonnull
    public abstract ActionResultType handle(@Nonnull TransfigurationContainer<?> transfigurationContainer);

    @Nonnull
    public abstract ItemStack getOutputRepresentation();
}
