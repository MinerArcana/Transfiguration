package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TransfigurationFinishedRecipe<T extends ISerializer<?>> implements FinishedRecipe {

    private final RecipeSerializer<?> recipeSerializer;
    private final ResourceLocation id;
    private final TransfigurationType transfigurationType;
    private final IFinishedObject<T> ingredient;
    private final IFinishedObject<ResultSerializer<?>> result;
    private final List<TransfigurationPredicate> predicates;
    private final int ticks;

    public TransfigurationFinishedRecipe(RecipeSerializer<?> recipeSerializer, ResourceLocation id,
                                         TransfigurationType transfigurationType, IFinishedObject<T> ingredient,
                                         IFinishedObject<ResultSerializer<?>> result,
                                         List<TransfigurationPredicate> predicates,
                                         int ticks) {
        this.recipeSerializer = recipeSerializer;
        this.id = id;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.predicates = predicates;
        this.ticks = ticks;
    }

    @Override
    public void serializeRecipeData(@Nonnull JsonObject json) {
        json.addProperty("transfigurationType", TransfigurationTypes.getKey(transfigurationType).toString());
        json.add("ingredient", ingredient.getJson());
        json.add("result", result.getJson());
        json.addProperty("ticks", ticks);
        if (!predicates.isEmpty()) {
            json.add(
                    "predicates",
                    TransfigurationPredicates.LIST_CODEC.get()
                            .encode(predicates, JsonOps.INSTANCE, JsonOps.INSTANCE.empty())
                            .getOrThrow(false, message -> {
                            })
            );
        }
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getType() {
        return recipeSerializer;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
