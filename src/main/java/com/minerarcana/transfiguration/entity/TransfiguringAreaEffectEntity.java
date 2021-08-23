package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

public class TransfiguringAreaEffectEntity extends AreaEffectCloudEntity {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(
            TransfiguringAreaEffectEntity.class,
            DataSerializers.STRING
    );

    private EntityTransfigurationRecipe recipe;
    private double timeModifier;
    private double powerModifier;
    private double radiusPerTransfiguring;

    public TransfiguringAreaEffectEntity(EntityType<? extends AreaEffectCloudEntity> cloud, World world) {
        super(cloud, world);
    }

    public TransfiguringAreaEffectEntity(World world, EntityTransfigurationRecipe recipe, double timeModifier, double powerModifier, double x, double y, double z) {
        this(TransfigurationEntities.TRANSFIGURING_AREA_EFFECT.get(), world);
        this.setPosition(x, y, z);
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.setColor(recipe.getTransfigurationType().getPrimaryColor());
        this.timeModifier = timeModifier;
        this.powerModifier = powerModifier;
        this.setRadius((float) timeModifier * 2);
        this.setRadiusOnUse(-2F);
        this.setWaitTime(10);
        this.setRadiusPerTick(this.getRadius() / this.getDuration());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager()
                .register(RECIPE_NAME, "");
    }

    @Override
    public void tick() {
        super.tick();

        EntityTransfigurationRecipe currentRecipe = this.getRecipe();
        if (currentRecipe != null && !this.world.isRemote() && this.ticksExisted % 5 == 0) {
            Iterator<Entity> entityList = this.getEntityWorld()
                    .getEntitiesInAABBexcluding(
                            this,
                            this.getBoundingBox(),
                            entity -> !(entity.getRidingEntity() instanceof TransfiguringEntity)
                    ).iterator();

            while (entityList.hasNext() && this.getRadius() > 0.5F) {
                Entity entity = entityList.next();
                TransfigurationContainer<Entity> container = TransfigurationContainer.entity(entity, null);
                if (currentRecipe.matches(container, world)) {
                    currentRecipe.transfigure(container, powerModifier, timeModifier);

                    float newRadius = this.getRadius();
                    if (this.radiusPerTransfiguring != 0.0F) {
                        newRadius += this.radiusPerTransfiguring;
                        if (newRadius < 0.5F) {
                            this.remove();
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
        return this.getEntityWorld()
                .getRecipeManager()
                .getRecipe(new ResourceLocation(this.getRecipeName()))
                .filter(EntityTransfigurationRecipe.class::isInstance)
                .map(EntityTransfigurationRecipe.class::cast)
                .orElse(null);
    }

    public void setRecipeName(String recipeName) {
        this.getDataManager().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getDataManager().get(RECIPE_NAME);
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
