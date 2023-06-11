package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeightedResult extends Result {
    private final SimpleWeightedRandomList<Result> weightedResults;
    private final RandomSource random;
    private final List<ItemStack> representations;

    public WeightedResult(List<Pair<Result, Integer>> results) {
        SimpleWeightedRandomList.Builder<Result> weightedBuilder = SimpleWeightedRandomList.builder();
        for (Pair<Result, Integer> result : results) {
            weightedBuilder.add(result.getFirst(), result.getSecond());
        }
        this.weightedResults = weightedBuilder.build();
        this.random = RandomSource.create();
        this.representations = results.stream()
                .map(Pair::getFirst)
                .flatMap(result -> result.getAllRepresentations().stream())
                .toList();
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

    @Override
    public List<ItemStack> getAllRepresentations() {
        return this.representations;
    }

    @NotNull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.WEIGHTED_RESULT_SERIALIZER.get();
    }
}
