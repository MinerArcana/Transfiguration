package com.minerarcana.transfiguration.recipe.ingedient.logic;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

public class NotIngredient extends BasicIngredient {
    private final List<BasicIngredient> ingredients;

    public NotIngredient(List<BasicIngredient> ingredient) {
        this.ingredients = ingredient;
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        for (BasicIngredient ingredient : ingredients) {
            if (ingredient.test(blockState)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        for (BasicIngredient ingredient : ingredients) {
            if (ingredient.test(entity)) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.NOT_INGREDIENT_SERIALIZER.get();
    }

    public List<BasicIngredient> getIngredients() {
        return ingredients;
    }
}
