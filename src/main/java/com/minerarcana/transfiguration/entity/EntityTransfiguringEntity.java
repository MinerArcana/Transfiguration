package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.TransfiguringKeyword;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityTransfiguringEntity extends TransfiguringEntity<EntityTransfigurationRecipe, Entity> {
    public static final EntityDataAccessor<Optional<UUID>> INPUT_UUID = SynchedEntityData.defineId(
            EntityTransfiguringEntity.class,
            EntityDataSerializers.OPTIONAL_UUID
    );

    private WeakReference<Entity> entityWeakReference;

    public EntityTransfiguringEntity(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
        this.entityWeakReference = new WeakReference<>(null);
    }

    public EntityTransfiguringEntity(Level world, Entity entity, EntityTransfigurationRecipe recipe, double timeModifier,
                                     double powerModifier) {
        super(TransfigurationEntities.ENTITY_TRANSFIGURING.get(), world, entity.blockPosition(), recipe, timeModifier, powerModifier);
        this.entityWeakReference = new WeakReference<>(entity);
        this.getEntityData().set(INPUT_UUID, Optional.of(entity.getUUID()));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(INPUT_UUID, Optional.empty());
    }

    @Nullable
    @Override
    public TransfigurationContainer<Entity> createTransfigurationContainer() {
        Entity entity = this.getInput();
        return entity == null ? null : new TransfigurationContainer<>(
                entity,
                this.getCaster(),
                this.getCommandSenderWorld(),
                entity.blockPosition(),
                Entity::discard
        );
    }

    @Override
    public void tick() {
        super.tick();
        Entity entity = this.getInput();
        if (entity != null && !this.getPassengers().contains(entity)) {
            entity.startRiding(this, true);
        }
    }

    @Override
    protected boolean spread(EntityTransfigurationRecipe recipe, TransfigurationContainer<Entity> container) {
        if (recipe.getTransfigurationType().hasKeyword(TransfiguringKeyword.CONTAGIOUS) && this.getTimeModifier() >= 1) {
            this.getCommandSenderWorld().addFreshEntity(new TransfiguringAreaEffectEntity(
                    level,
                    recipe,
                    this.getTimeModifier() / 2,
                    Math.ceil(this.getPowerModifier() / (this.isSkipping() ? 1D : 2.0D)),
                    this.getX(),
                    this.getY(),
                    this.getZ()
            ));
        }
        return true;
    }

    @Nullable
    @Override
    public EntityTransfigurationRecipe getRecipe() {
        return this.getCommandSenderWorld()
                .getRecipeManager()
                .byKey(new ResourceLocation(this.getRecipeName()))
                .filter(EntityTransfigurationRecipe.class::isInstance)
                .map(EntityTransfigurationRecipe.class::cast)
                .orElse(null);
    }

    @Override
    public void removeInput() {
        Entity entity = this.getInput();
        if (entity != null && entity.isAlive()) {
            entity.discard();
        }
    }

    @Nullable
    private Entity getInput() {
        Entity entity = this.entityWeakReference.get();
        if (entity != null) {
            return entity;
        } else {
            UUID uuid = this.getEntityData().get(INPUT_UUID)
                    .orElse(null);
            if (uuid == null) {
                return null;
            } else if (this.getCommandSenderWorld() instanceof ServerLevel) {
                entity = ((ServerLevel) this.getCommandSenderWorld()).getEntity(uuid);
                if (entity != null) {
                    this.entityWeakReference = new WeakReference<>(entity);
                    return entity;
                }
            } else {
                List<Entity> entityList = this.getCommandSenderWorld().getEntities(
                        this,
                        this.getBoundingBox().inflate(2),
                        value -> value.getUUID().equals(uuid)
                );
                if (!entityList.isEmpty()) {
                    entity = entityList.get(0);
                    this.entityWeakReference = new WeakReference<>(entity);
                    return entity;
                }
            }
        }
        return null;
    }

    public double getPassengersRidingOffset() {
        return -0.5D;
    }
}
