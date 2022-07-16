package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.event.TransfigurationEvent;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

public class BlockTransfigurationRecipe extends TransfigurationRecipe<BlockState> {
    public BlockTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                      BasicIngredient ingredient, Result result, TransfigurationPredicate[] predicate,
                                      int ticks) {
        super(recipeId, transfigurationType, ingredient, result, predicate, ticks);
    }

    @Override
    public TransfiguringEntity<?, BlockState> createTransfiguringEntity(TransfigurationContainer<BlockState> transfigurationContainer, double timeModifier, double powerModifier) {
        return new BlockTransfiguringEntity(
                transfigurationContainer.getLevel(),
                transfigurationContainer.getTargetedPos(),
                this,
                timeModifier,
                powerModifier
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(TransfigurationContainer<BlockState> container, Level world) {
        return this.getIngredient().test(container.getTargeted()) && super.matches(container);
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return this.getTransfigurationType().getBlockRecipeType();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryTransfigure(TransfigurationType type, BlockHitResult blockRayTraceResult, Level world,
                                         @Nullable Entity entity, double powerModifier, double timeModifier) {
        return tryTransfigure(type, TransfigurationContainer.block(
                world,
                blockRayTraceResult.getBlockPos(),
                entity
        ), powerModifier, timeModifier);
    }

    public static boolean tryTransfigure(TransfigurationType type, TransfigurationContainer<BlockState> container,
                                         double powerModifier, double timeModifier) {
        if (type != null && !containsTransfiguringEntity(container.getLevel(), container.getTargetedPos())) {
            Level world = container.getLevel();
            Optional<ITransfigurationRecipe<BlockState>> recipeOptional = world.getRecipeManager()
                    .getRecipeFor(type.getBlockRecipeType(), container, world);
            TransfigurationEvent transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
            MinecraftForge.EVENT_BUS.post(transfigurationEvent);
            if (!recipeOptional.isPresent()) {
                Iterator<Supplier<TransfigurationType>> supplierIterator = type.getFallbacks().iterator();
                while (!recipeOptional.isPresent() && supplierIterator.hasNext()) {
                    TransfigurationType nextType = supplierIterator.next().get();
                    transfigurationEvent = new TransfigurationEvent(type, container, timeModifier, powerModifier);
                    MinecraftForge.EVENT_BUS.post(transfigurationEvent);
                    recipeOptional = world.getRecipeManager()
                            .getRecipeFor(nextType.getBlockRecipeType(), container, world);
                }
            }
            double finalPowerModifier = transfigurationEvent.getCurrentPowerModifier();
            double finalTimeModifier = transfigurationEvent.getCurrentTimeModifier();
            return recipeOptional.map(recipe -> recipe.transfigure(container, finalPowerModifier, finalTimeModifier))
                    .orElse(false);
        }

        return false;
    }

    private static boolean containsTransfiguringEntity(Level world, BlockPos blockPos) {
        return world.getEntitiesOfClass(TransfiguringEntity.class, new AABB(blockPos))
                .stream()
                .anyMatch(transfiguringEntity -> transfiguringEntity.blockPosition().equals(blockPos));
    }
}
