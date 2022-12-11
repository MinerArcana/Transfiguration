package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class WeightedResult extends Result {
    private final SimpleWeightedRandomList<Result> weightedResults;
    private final RandomSource random;

    public WeightedResult(List<Pair<Result, Integer>> results) {
        SimpleWeightedRandomList.Builder<Result> weightedBuilder = SimpleWeightedRandomList.builder();
        for (Pair<Result, Integer> result : results) {
            weightedBuilder.add(result.getFirst(), result.getSecond());
        }
        this.weightedResults = weightedBuilder.build();
        this.random = RandomSource.create();
    }

    public SimpleWeightedRandomList<Result> getWeightedResults() {
        return this.weightedResults;
    }

    @NotNull
    @Override
    public ResultInstance create() {
        return weightedResults.getRandomValue(random)
                .orElseThrow()
                .create();
    }

    @NotNull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.WEIGHTED_RESULT_SERIALIZER.get();
    }
}
