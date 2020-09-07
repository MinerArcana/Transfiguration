package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;

public abstract class Result implements ISerializable<ResultSerializer<?>> {

    public abstract ActionResultType handle(TransfigurationContainer<?> transfigurationContainer);

    public abstract ItemStack getOutputRepresentation();
}
