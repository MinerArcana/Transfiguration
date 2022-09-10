package com.minerarcana.transfiguration.recipe.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class WeightedResultBuilder {
    private final List<Pair<FinishedObject<ResultSerializer<?>>, Integer>> results;
    private final ResourceLocation id;

    public WeightedResultBuilder(ResourceLocation id) {
        this.id = id;
        this.results = Lists.newArrayList();
    }

    public WeightedResultBuilder addResult(int weight, FinishedObject<ResultSerializer<?>> result) {
        this.results.add(Pair.of(result, weight));
        return this;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public FinishedObject<ResultSerializer<?>> build() {
        return new FinishedObject<>(
                TransfigurationRecipes.WEIGHTED_RESULT_SERIALIZER.get(),
                this::getId,
                this::generate
        );
    }

    private void generate(JsonObject jsonObject) {
        JsonArray jsonArray = new JsonArray();
        for (Pair<FinishedObject<ResultSerializer<?>>, Integer> result : results) {
            if (result.getSecond() <= 0) {
                throw new IllegalArgumentException(result.getFirst().getId() + " must have a weight greater than 0");
            } else {
                JsonObject entryObject = new JsonObject();
                entryObject.addProperty("weight", result.getSecond());
                entryObject.add("result", result.getFirst().getJson());
                jsonArray.add(entryObject);
            }
        }
        jsonObject.add("entries", jsonArray);
    }

    public static WeightedResultBuilder of(ResourceLocation id) {
        return new WeightedResultBuilder(id);
    }

}
