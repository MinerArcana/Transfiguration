package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class TransfigurationRecipeBuilder<T extends ISerializer<?> & IForgeRegistryEntry<T>> {
    private final TransfigurationType transfigurationType;
    private final IRecipeSerializer<?> recipeSerializer;
    private final String recipeType;
    private IFinishedObject<T> ingredient;
    private IFinishedObject<ResultSerializer<?>> result;
    private Advancement.Builder advancementBuilder;

    private TransfigurationRecipeBuilder(TransfigurationType transfigurationType, IRecipeSerializer<?> recipeSerializer,
                                         String recipeType) {
        this.transfigurationType = transfigurationType;
        this.recipeSerializer = recipeSerializer;
        this.recipeType = recipeType;
    }

    public static TransfigurationRecipeBuilder<BlockIngredientSerializer<?>> block(TransfigurationType transfigurationType) {
        return new TransfigurationRecipeBuilder<>(transfigurationType, TransfigurationRecipes.BLOCK_TRANSFIGURATION.get(),
                "block");
    }

    public static TransfigurationRecipeBuilder<EntityIngredientSerializer<?>> entity(TransfigurationType transfigurationType) {
        return new TransfigurationRecipeBuilder<>(transfigurationType, TransfigurationRecipes.ENTITY_TRANSFIGURATION.get(),
                "entity");
    }

    public TransfigurationRecipeBuilder<T> withIngredient(IFinishedObject<T> ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public TransfigurationRecipeBuilder<T> withResult(IFinishedObject<ResultSerializer<?>> result) {
        this.result = result;
        return this;
    }

    public TransfigurationRecipeBuilder<T> withCriterion(String name, ICriterionInstance criterionInstance) {
        if (this.advancementBuilder == null) {
            this.advancementBuilder = Advancement.Builder.builder();
        }
        this.advancementBuilder.withCriterion(name, criterionInstance);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer) {
        this.build(recipeConsumer, null);
    }

    public void build(Consumer<IFinishedRecipe> recipeConsumer, @Nullable ResourceLocation id) {
        this.validate(id);
        if (id == null) {
            ResourceLocation resultId = this.result.getId();
            id = new ResourceLocation(resultId.getNamespace(), "transfiguration/" + resultId.getPath() + "_from_" +
                    Objects.requireNonNull(transfigurationType.getRegistryName()).getPath() + "_" +
                    ingredient.getId().toString().replace(":", "_"));
        }
        if (this.advancementBuilder != null) {
            this.advancementBuilder.withParentId(new ResourceLocation("recipes/root"))
                    .withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id))
                    .withRewards(AdvancementRewards.Builder.recipe(id))
                    .withRequirementsStrategy(IRequirementsStrategy.OR);
        }
        recipeConsumer.accept(new TransfigurationFinishedRecipe<>(recipeSerializer, recipeType, id, transfigurationType,
                ingredient, result, advancementBuilder));
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
