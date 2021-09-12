package com.minerarcana.transfiguration.recipe.ingedient.logic;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;
import java.util.List;

public class AndIngredient extends BasicIngredient {
    private final List<BasicIngredient> basicIngredientList;

    public AndIngredient(List<BasicIngredient> basicIngredientList) {
        this.basicIngredientList = basicIngredientList;
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        for (BasicIngredient ingredient : basicIngredientList) {
            if (!ingredient.test(entity)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        for (BasicIngredient ingredient : basicIngredientList) {
            if (!ingredient.test(blockState)) {
                return false;
            }
        }
        return true;
    }

    public List<BasicIngredient> getBasicIngredientList() {
        return basicIngredientList;
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.AND_INGREDIENT_SERIALIZER.get();
    }
}
