package com.minerarcana.transfiguration.recipe.entity;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.event.TransfigurationEvent;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.EntityTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

public class EntityTransfigurationRecipe extends TransfigurationRecipe<Entity> {
    public EntityTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                       BasicIngredient ingredient, Result result, TransfigurationPredicate[] predicate,
                                       int time, float skip) {
        super(recipeId, transfigurationType, ingredient, result, predicate, time, skip);
    }


    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(TransfigurationContainer<Entity> inv, Level world) {
        return this.getIngredient().test(inv.getTargeted());
    }

    @Override
    public TransfiguringEntity<?, Entity> createTransfiguringEntity(
            TransfigurationContainer<Entity> transfigurationContainer, double timeModifier, double powerModifier) {
        return new EntityTransfiguringEntity(
                transfigurationContainer.getLevel(),
                transfigurationContainer.getTargeted(),
                this,
                timeModifier,
                powerModifier
        );
    }


    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return this.getTransfigurationType().getEntityRecipeType();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryTransfigure(TransfigurationType type, Entity target, @Nullable Entity caster,
                                         double powerModifier, double timeModifier) {
        return tryTransfigure(type, TransfigurationContainer.entity(
                target,
                caster
        ), powerModifier, timeModifier);
    }

    public static boolean tryTransfigure(TransfigurationType type, TransfigurationContainer<Entity> container,
                                         double powerModifier, double timeModifier) {
        if (type != null && !(container.getTargeted().getVehicle() instanceof TransfiguringEntity<?, ?>)) {
            Level world = container.getLevel();
            Optional<ITransfigurationRecipe<Entity>> recipeOptional = world.getRecipeManager()
                    .getRecipeFor(type.getEntityRecipeType(), container, world);
            TransfigurationEvent transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
            MinecraftForge.EVENT_BUS.post(transfigurationEvent);
            if (recipeOptional.isEmpty()) {
                Iterator<Supplier<TransfigurationType>> supplierIterator = type.getFallbacks().iterator();
                while (recipeOptional.isEmpty() && supplierIterator.hasNext()) {
                    TransfigurationType nextType = supplierIterator.next().get();
                    transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
                    MinecraftForge.EVENT_BUS.post(transfigurationEvent);
                    recipeOptional = world.getRecipeManager()
                            .getRecipeFor(nextType.getEntityRecipeType(), container, world);
                }
            }
            double finalPowerModifier = transfigurationEvent.getCurrentPowerModifier();
            double finalTimeModifier = transfigurationEvent.getCurrentTimeModifier();
            return recipeOptional.map(recipe -> recipe.transfigure(container, finalPowerModifier, finalTimeModifier))
                    .orElse(false);
        }

        return false;
    }
}
