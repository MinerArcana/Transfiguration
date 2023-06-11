package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.ChanceResultInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Random;

public class ChanceResult extends Result {
    private final Random random;
    private final float chance;
    private final Result result;

    public ChanceResult(float chance, Result result) {
        this.random = new Random(System.currentTimeMillis());
        this.chance = chance;
        this.result = result;
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new ChanceResultInstance(result.create(), random.nextFloat() < chance);
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return result.getRepresentation();
    }

    @Override
    public Ingredient getView() {
        return this.result.getView();
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.CHANCE_RESULT_SERIALIZER.get();
    }

    public float getChance() {
        return chance;
    }

    public Result getResult() {
        return result;
    }
}
