package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<V>, V> extends Entity {
    private static final EntityDataAccessor<String> RECIPE_NAME = SynchedEntityData.defineId(
            TransfiguringEntity.class,
            EntityDataSerializers.STRING
    );

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double timeModifier;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultInstance resultInstance;
    private boolean hasTriggered;
    private boolean hasSpread;

    public TransfiguringEntity(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
        this.hasTriggered = false;
    }

    public TransfiguringEntity(EntityType<? extends Entity> entityType, Level world, BlockPos blockPos, T recipe,
                               double timeModifier, double powerModifier) {
        super(entityType, world);
        this.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = (int) Math.ceil(recipe.getTicks() / timeModifier);
        this.timeModifier = timeModifier;
        this.powerModifier = powerModifier;
        this.hasTriggered = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getCommandSenderWorld().isClientSide()) {
            if (recipe == null) {
                recipe = this.getRecipe();
                noRecipeTicks++;
                if (noRecipeTicks > 5) {
                    this.discard();
                }
            } else {
                T currentRecipe = this.getRecipe();
                TransfigurationContainer<V> transfigurationContainer = this.createTransfigurationContainer();
                if (currentRecipe != null && transfigurationContainer != null) {
                    if (!currentRecipe.matches(transfigurationContainer, this.getCommandSenderWorld())) {
                        this.discard();
                    }
                    int remainingTicks = this.modifiedTime - (int) (this.getCommandSenderWorld().getGameTime() - startTime);
                    if (!hasSpread && remainingTicks < this.modifiedTime / 2) {
                        this.hasSpread = this.spread(currentRecipe, transfigurationContainer);
                    }

                    Level world = this.getCommandSenderWorld();
                    if (world instanceof ServerLevel) {
                        Vec3 startPos = Vectors.withRandomOffset(this.blockPosition(), world.getRandom(), 3);
                        Vec3 endPos = Vectors.centered(this.blockPosition());
                        ((ServerLevel) world).sendParticles(
                                new TransfiguringParticleData(
                                        recipe.getTransfigurationType(),
                                        endPos,
                                        10,
                                        Math.min(remainingTicks, 45),
                                        random.nextInt(32)
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
                    if (this.getResultInstance(currentRecipe)
                            .tick(transfigurationContainer, powerModifier, remainingTicks, this::trigger)) {
                        this.discard();
                    }
                }
            }
        }
    }

    protected abstract boolean spread(T recipe, TransfigurationContainer<V> container);

    private boolean trigger(boolean removeInput) {
        int remainingTicks = this.modifiedTime - (int) (this.getCommandSenderWorld().getGameTime() - startTime);
        if (remainingTicks <= 0 && !this.hasTriggered) {
            if (removeInput) {
                this.removeInput();
            }

            this.getCommandSenderWorld().playSound(
                    null,
                    this.blockPosition(),
                    SoundEvents.ILLUSIONER_CAST_SPELL,
                    SoundSource.PLAYERS,
                    1,
                    1
            );
            this.hasTriggered = true;
            return true;
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(RECIPE_NAME, "");
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        compound.putString("recipeName", this.getRecipeName());
        compound.putLong("startTime", this.startTime);
        compound.putInt("modifiedTime", this.modifiedTime);
        compound.putDouble("powerModifier", this.powerModifier);
    }

    @Override
    @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setRecipeName(String recipeName) {
        this.getEntityData().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getEntityData().get(RECIPE_NAME);
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

    protected double getTimeModifier() {
        return timeModifier;
    }

    protected double getPowerModifier() {
        return powerModifier;
    }
}
