package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class EntityResult extends Result {
    private final EntityType<?> entityType;

    public EntityResult(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(1, this::handle);
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        summon(transfigurationContainer, entityType);
    }

    @Override
    @Nonnull
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get();
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public static EntityResult create(EntityType<?> entityType) {
        return new EntityResult(entityType);
    }

    public static InteractionResult summon(TransfigurationContainer<?> transfigurationContainer, EntityType<?> entityType) {
        Entity entity = entityType.create(transfigurationContainer.getLevel());
        if (entity != null) {
            if (transfigurationContainer.getLevel().isClientSide()) {
                return InteractionResult.CONSUME;
            } else {
                BlockPos blockPos = transfigurationContainer.getTargetedPos();
                entity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                transfigurationContainer.getLevel().addFreshEntity(entity);
                return InteractionResult.SUCCESS;
            }

        } else {
            return InteractionResult.FAIL;
        }
    }
}
