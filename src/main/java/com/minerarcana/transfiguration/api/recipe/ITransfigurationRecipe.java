package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.world.item.crafting.Recipe;

public interface ITransfigurationRecipe<T> extends Recipe<TransfigurationContainer<T>> {
    boolean transfigure(TransfigurationContainer<T> container, double powerModifier, double timeModifier);
}
