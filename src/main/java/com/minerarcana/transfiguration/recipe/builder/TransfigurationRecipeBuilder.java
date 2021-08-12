package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TransfigurationRecipeBuilder<T extends ISerializer<?> & IForgeRegistryEntry<T>> {
    private final TransfigurationType transfigurationType;
    private final IRecipeSerializer<?> recipeSerializer;
    private IFinishedObject<T> ingredient;
    private IFinishedObject<ResultSerializer<?>> result;
    private int ticks = 12;

    private TransfigurationRecipeBuilder(TransfigurationType transfigurationType, IRecipeSerializer<?> recipeSerializer) {
        this.transfigurationType = transfigurationType;
        this.recipeSerializer = recipeSerializer;
    }

    public static TransfigurationRecipeBuilder<BlockIngredientSerializer<?>> block(TransfigurationType transfigurationType) {
        return new TransfigurationRecipeBuilder<>(transfigurationType, TransfigurationRecipes.BLOCK_TRANSFIGURATION.get());
    }

    public static TransfigurationRecipeBuilder<BlockIngredientSerializer<?>> block(Supplier<? extends TransfigurationType> transfigurationType) {
        return block(transfigurationType.get());
    }

    public static TransfigurationRecipeBuilder<EntityIngredientSerializer<?>> entity(TransfigurationType transfigurationType) {
        return new TransfigurationRecipeBuilder<>(transfigurationType, TransfigurationRecipes.ENTITY_TRANSFIGURATION.get());
    }

    public static TransfigurationRecipeBuilder<EntityIngredientSerializer<?>> entity(Supplier<? extends TransfigurationType> transfigurationType) {
        return entity(transfigurationType.get());
    }

    public TransfigurationRecipeBuilder<T> withIngredient(IFinishedObject<T> ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public TransfigurationRecipeBuilder<T> withResult(IFinishedObject<ResultSerializer<?>> result) {
        this.result = result;
        return this;
    }

    public TransfigurationRecipeBuilder<T> withTicks(int ticks) {
        this.ticks = ticks;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer) {
        this.build(recipeConsumer, null);
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer, @Nullable ResourceLocation id) {
        this.validate(id);
        if (id == null) {
            ResourceLocation resultId = this.result.getId();
            id = new ResourceLocation(resultId.getNamespace(), "transfiguration/" + resultId.getPath().replace("/", "_")
                    + "_from_" + Objects.requireNonNull(transfigurationType.getRegistryName()).getPath().replace("/", "_")
                    + "_" + ingredient.getId().toString().replace(":", "_").replace("/", "_"));
        }
        recipeConsumer.accept(new TransfigurationFinishedRecipe<>(recipeSerializer, id, transfigurationType,
                ingredient, result, ticks));
    }

    protected void validate(ResourceLocation id) {
        if (result == null) {
            throw new IllegalStateException("No 'result' defined for Transfiguration Recipe " + id + "!");
        }

        if (ingredient == null) {
            throw new IllegalStateException("No 'ingredient' defined for Transfiguration Recipe " + id + "!");
        }

        if (transfigurationType == null) {
            throw new IllegalStateException("No 'transfigurationType' defined for Transfiguration Recipe " + id + "!");
        }
    }
}
