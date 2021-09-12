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
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.entity.Entity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

public class EntityTransfigurationRecipe extends TransfigurationRecipe<Entity> {
    public EntityTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                       BasicIngredient ingredient, Result result, int time) {
        super(recipeId, transfigurationType, ingredient, result, time);
    }


    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(TransfigurationContainer<Entity> inv, World world) {
        return this.getIngredient().test(inv.getTargeted());
    }

    @Override
    public TransfiguringEntity<?, Entity> createTransfiguringEntity(
            TransfigurationContainer<Entity> transfigurationContainer, double timeModifier, double powerModifier) {
        return new EntityTransfiguringEntity(
                transfigurationContainer.getWorld(),
                transfigurationContainer.getTargeted(),
                this,
                timeModifier,
                powerModifier
        );
    }


    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
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
        if (type != null) {
            World world = container.getWorld();
            Optional<ITransfigurationRecipe<Entity>> recipeOptional = world.getRecipeManager()
                    .getRecipe(type.getEntityRecipeType(), container, world);
            TransfigurationEvent transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
            MinecraftForge.EVENT_BUS.post(transfigurationEvent);
            if (!recipeOptional.isPresent()) {
                Iterator<Supplier<TransfigurationType>> supplierIterator = type.getFallbacks().iterator();
                while (!recipeOptional.isPresent() && supplierIterator.hasNext()) {
                    TransfigurationType nextType = supplierIterator.next().get();
                    transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
                    MinecraftForge.EVENT_BUS.post(transfigurationEvent);
                    recipeOptional = world.getRecipeManager()
                            .getRecipe(nextType.getEntityRecipeType(), container, world);
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
