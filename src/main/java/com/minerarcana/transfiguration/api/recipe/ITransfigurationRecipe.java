package com.minerarcana.transfiguration.api.recipe;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Random;

public interface ITransfigurationRecipe<T> extends Recipe<TransfigurationContainer<T>> {
    boolean transfigure(TransfigurationContainer<T> container, double powerModifier, double timeModifier);

    boolean doSkip(RandomSource random);

    BasicIngredient getIngredient();

    Result getResult();

    TransfigurationType getTransfigurationType();

    Ingredient getViewIngredient();

    Ingredient getViewResults();
}
