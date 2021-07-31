package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.item.crafting.IRecipe;

public interface ITransfigurationRecipe<T> extends IRecipe<TransfigurationContainer<T>> {
    boolean transfigure(TransfigurationContainer<T> container, double powerModifier);
}
