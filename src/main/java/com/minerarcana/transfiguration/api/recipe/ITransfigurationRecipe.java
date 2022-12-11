package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Random;

public interface ITransfigurationRecipe<T> extends Recipe<TransfigurationContainer<T>> {
    boolean transfigure(TransfigurationContainer<T> container, double powerModifier, double timeModifier);

    boolean doSkip(RandomSource random);
}
