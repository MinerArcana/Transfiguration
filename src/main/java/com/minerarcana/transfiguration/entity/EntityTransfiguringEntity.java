package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.TransfiguringKeyword;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityTransfiguringEntity extends TransfiguringEntity<EntityTransfigurationRecipe, Entity> {
    public static final DataParameter<Optional<UUID>> INPUT_UUID = EntityDataManager.createKey(
            EntityTransfiguringEntity.class,
            DataSerializers.OPTIONAL_UNIQUE_ID
    );

    private WeakReference<Entity> entityWeakReference;

    public EntityTransfiguringEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.entityWeakReference = new WeakReference<>(null);
    }

    public EntityTransfiguringEntity(World world, Entity entity, EntityTransfigurationRecipe recipe, double timeModifier,
                                     double powerModifier) {
        super(TransfigurationEntities.ENTITY_TRANSFIGURING.get(), world, entity.getPosition(), recipe, timeModifier, powerModifier);
        this.entityWeakReference = new WeakReference<>(entity);
        this.getDataManager().set(INPUT_UUID, Optional.of(entity.getUniqueID()));
    }

    @Override
    public void registerData() {
        super.registerData();
        this.getDataManager().register(INPUT_UUID, Optional.empty());
    }

    @Nullable
    @Override
    public TransfigurationContainer<Entity> createTransfigurationContainer() {
        Entity entity = this.getInput();
        return entity == null ? null : new TransfigurationContainer<>(
                entity,
                this.getCaster(),
                this.getEntityWorld(),
                entity.getPosition(),
                Entity::remove
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
            this.getEntityWorld().addEntity(new TransfiguringAreaEffectEntity(
                    world,
                    recipe,
                    this.getTimeModifier() / 2,
                    Math.ceil(this.getPowerModifier() / 2),
                    this.getPosX(),
                    this.getPosY(),
                    this.getPosZ()
            ));
        }
        return true;
    }

    @Nullable
    @Override
    public EntityTransfigurationRecipe getRecipe() {
        return this.getEntityWorld()
                .getRecipeManager()
                .getRecipe(new ResourceLocation(this.getRecipeName()))
                .filter(EntityTransfigurationRecipe.class::isInstance)
                .map(EntityTransfigurationRecipe.class::cast)
                .orElse(null);
    }

    @Override
    public void removeInput() {
        Entity entity = this.getInput();
        if (entity != null && entity.isAlive()) {
            entity.remove();
        }
    }

    @Nullable
    private Entity getInput() {
        Entity entity = this.entityWeakReference.get();
        if (entity != null) {
            return entity;
        } else {
            UUID uuid = this.getDataManager().get(INPUT_UUID)
                    .orElse(null);
            if (uuid == null) {
                return null;
            } else if (this.getEntityWorld() instanceof ServerWorld) {
                entity = ((ServerWorld) this.getEntityWorld()).getEntityByUuid(uuid);
                if (entity != null) {
                    this.entityWeakReference = new WeakReference<>(entity);
                    return entity;
                }
            } else {
                List<Entity> entityList = this.getEntityWorld().getEntitiesInAABBexcluding(
                        this,
                        this.getBoundingBox().grow(2),
                        value -> value.getUniqueID().equals(uuid)
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

    public double getMountedYOffset() {
        return -0.5D;
    }
}
