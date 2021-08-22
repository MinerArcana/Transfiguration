package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.NonNullPredicate;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<U, V>, U extends NonNullPredicate<V>, V>
        extends Entity {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(
            TransfiguringEntity.class,
            DataSerializers.STRING
    );

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultInstance resultInstance;
    private boolean hasTriggered;

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.hasTriggered = false;
    }

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world, BlockPos blockPos, T recipe,
                               int modifiedTime, double powerModifier) {
        super(entityType, world);
        this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = modifiedTime;
        this.powerModifier = powerModifier;
        this.hasTriggered = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getEntityWorld().isRemote()) {

            if (recipe == null) {
                recipe = this.getRecipe();
                noRecipeTicks++;
                if (noRecipeTicks > 5) {
                    this.remove();
                }
            } else {
                int remainingTicks = this.modifiedTime - (int) (this.getEntityWorld().getGameTime() - startTime);
                TransfigurationContainer<V> transfigurationContainer = this.createTransfigurationContainer();
                if (transfigurationContainer != null) {
                    World world = this.getEntityWorld();
                    if (world instanceof ServerWorld) {
                        Vector3d startPos = Vectors.withRandomOffset(this.getPosition(), world.getRandom(), 3);
                        Vector3d endPos = Vectors.centered(this.getPosition());
                        ((ServerWorld) world).spawnParticle(
                                new TransfiguringParticleData(
                                        recipe.getTransfigurationType(),
                                        endPos,
                                        10,
                                        Math.min(remainingTicks, 60)
                                ),
                                startPos.x,
                                startPos.y,
                                startPos.z,
                                1,
                                0.0D,
                                0.0D,
                                0.0D,
                                0.15F
                        );
                    }
                    if (this.getResultInstance(recipe).tick(transfigurationContainer, powerModifier, remainingTicks, this::trigger)) {
                        this.remove();
                    }
                } else {
                    this.remove();
                }
            }
        }
    }

    private boolean trigger(boolean removeInput) {
        int remainingTicks = this.modifiedTime - (int) (this.getEntityWorld().getGameTime() - startTime);
        if (remainingTicks <= 0 && !this.hasTriggered) {
            if (removeInput) {
                this.removeInput();
            }

            this.getEntityWorld().playSound(
                    null,
                    this.getPosition(),
                    SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL,
                    SoundCategory.PLAYERS,
                    1,
                    1
            );
            this.hasTriggered = true;
            return true;
        }
        return false;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(RECIPE_NAME, "");
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putString("recipeName", this.getRecipeName());
        compound.putLong("startTime", this.startTime);
        compound.putInt("modifiedTime", this.modifiedTime);
        compound.putDouble("powerModifier", this.powerModifier);
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setRecipeName(String recipeName) {
        this.getDataManager().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getDataManager().get(RECIPE_NAME);
    }

    @Nullable
    public Entity getCaster() {
        return null;
    }

    @Nonnull
    public ResultInstance getResultInstance(@Nonnull T recipe) {
        if (this.resultInstance == null) {
            this.resultInstance = recipe.getResult().create();
        }
        return this.resultInstance;
    }

    @Nullable
    public abstract TransfigurationContainer<V> createTransfigurationContainer();

    @Nullable
    public abstract T getRecipe();

    public abstract void removeInput();
}
