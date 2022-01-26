package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

public class TransfiguringAreaEffectEntity extends AreaEffectCloud {
    private static final EntityDataAccessor<String> RECIPE_NAME = SynchedEntityData.defineId(
            TransfiguringAreaEffectEntity.class,
            EntityDataSerializers.STRING
    );

    private EntityTransfigurationRecipe recipe;
    private double timeModifier;
    private double powerModifier;
    private double radiusPerTransfiguring;

    public TransfiguringAreaEffectEntity(EntityType<? extends AreaEffectCloud> cloud, Level world) {
        super(cloud, world);
    }

    public TransfiguringAreaEffectEntity(Level world, EntityTransfigurationRecipe recipe, double timeModifier, double powerModifier, double x, double y, double z) {
        this(TransfigurationEntities.TRANSFIGURING_AREA_EFFECT.get(), world);
        this.setPos(x, y, z);
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.setFixedColor(recipe.getTransfigurationType().getPrimaryColor());
        this.timeModifier = timeModifier;
        this.powerModifier = powerModifier;
        this.setRadius((float) timeModifier * 2);
        this.setRadiusOnUse(-2F);
        this.setWaitTime(10);
        this.setRadiusPerTick(this.getRadius() / this.getDuration());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData()
                .define(RECIPE_NAME, "");
    }

    @Override
    public void tick() {
        super.tick();

        EntityTransfigurationRecipe currentRecipe = this.getRecipe();
        if (currentRecipe != null && !this.level.isClientSide() && this.tickCount % 5 == 0) {
            Iterator<Entity> entityList = this.getCommandSenderWorld()
                    .getEntities(
                            this,
                            this.getBoundingBox(),
                            entity -> !(entity.getVehicle() instanceof TransfiguringEntity)
                    ).iterator();

            while (entityList.hasNext() && this.getRadius() > 0.5F) {
                Entity entity = entityList.next();
                TransfigurationContainer<Entity> container = TransfigurationContainer.entity(entity, null);
                if (currentRecipe.matches(container, level)) {
                    currentRecipe.transfigure(container, powerModifier, timeModifier);

                    float newRadius = this.getRadius();
                    if (this.radiusPerTransfiguring != 0.0F) {
                        newRadius += this.radiusPerTransfiguring;
                        if (newRadius < 0.5F) {
                            this.discard();
                        }
                        this.setRadius(newRadius);
                    }

                }
            }
        }
    }

    @Override
    public void setRadiusOnUse(float radiusOnUseIn) {
        super.setRadiusOnUse(radiusOnUseIn);
        this.radiusPerTransfiguring = radiusOnUseIn;
    }

    @Nullable
    public EntityTransfigurationRecipe getRecipe() {
        return this.getCommandSenderWorld()
                .getRecipeManager()
                .byKey(new ResourceLocation(this.getRecipeName()))
                .filter(EntityTransfigurationRecipe.class::isInstance)
                .map(EntityTransfigurationRecipe.class::cast)
                .orElse(null);
    }

    public void setRecipeName(String recipeName) {
        this.getEntityData().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getEntityData().get(RECIPE_NAME);
    }

    @Override
    @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
