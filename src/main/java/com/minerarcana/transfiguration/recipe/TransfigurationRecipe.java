package com.minerarcana.transfiguration.recipe;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public abstract class TransfigurationRecipe<U> implements ITransfigurationRecipe<U> {
    private final ResourceLocation recipeId;
    private final TransfigurationType transfigurationType;
    private final BasicIngredient ingredient;
    private final Result result;
    private final TransfigurationPredicate[] predicates;
    private final Predicate<TransfigurationContainer<?>> combinedPredicate;
    private final int ticks;

    public TransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType, BasicIngredient ingredient,
                                 Result result, TransfigurationPredicate[] predicates, int ticks) {
        this.recipeId = recipeId;
        this.transfigurationType = transfigurationType;
        this.ingredient = ingredient;
        this.result = result;
        this.predicates = predicates;
        this.combinedPredicate = LootItemConditions.andConditions(predicates);
        this.ticks = ticks;
    }

    protected boolean matches(@Nonnull TransfigurationContainer<?> transfigurationContainer) {
        return this.combinedPredicate.test(transfigurationContainer);
    }

    @Override
    @Nonnull
    public ItemStack assemble(@Nonnull TransfigurationContainer<U> transfigurationContainer) {
        return result.getRepresentation().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        return result.getRepresentation();
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public boolean transfigure(TransfigurationContainer<U> transfigurationContainer, double powerModifier, double timeModifier) {
        return transfigurationContainer.getLevel().isClientSide() || transfigurationContainer.getLevel().addFreshEntity(
                this.createTransfiguringEntity(transfigurationContainer, timeModifier, powerModifier)
        );
    }

    public abstract TransfiguringEntity<?, U> createTransfiguringEntity(
            TransfigurationContainer<U> transfigurationContainer, double timeModifier, double powerModifier
    );

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public BasicIngredient getIngredient() {
        return ingredient;
    }

    public Result getResult() {
        return result;
    }

    public int getTicks() {
        return ticks;
    }


    public TransfigurationPredicate[] getPredicates() {
        return predicates;
    }
}
